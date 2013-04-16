package com.github.sunlong.hellomonitor.user.service;

import com.github.sunlong.hellomonitor.common.Digests;
import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.common.SortBean;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.user.dao.IUserDao;
import com.github.sunlong.hellomonitor.user.model.User;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: sunlong
 * Date: 13-1-30
 * Time: 下午5:10
 */
@Service
@Transactional(readOnly = true)
public class UserService {
    @Resource
    private IUserDao userDao;

    @Resource
    private Validator validator;
    
    public static final int HASH_ITERATIONS = 256;
    public static final String HASH_ALGORITHM = "SHA-1";
    private static final int SALT_SIZE = 8;

    public User findUserByUsername(String username) throws AppException {
        User user = userDao.findByUsername(username);
        if(user == null){
            throw new AppException(MessageCode.USER_NOT_EXIST_ERROR, username);
        }
        Hibernate.initialize(user.getRole().getPermissions());
        return user;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void save(User user) throws AppException {
        user.setCreatedDate(new Date());
        user.setDefault(false);
        user.validate(validator);

        User userByName = userDao.findByUsername(user.getUsername());
        if(userByName != null){
            throw new AppException(MessageCode.USER_NAME_EXIST_ERROR, user.getUsername());
        }
        encryptPassword(user);
        userDao.save(user);
    }

    public Page<User> list(int pageNumber, int pageSize, final Map<String, Object> params, SortBean sortBean) {
        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
                if(params!=null){
                    List<Predicate> predicates = new ArrayList<Predicate>();

                    String username = (String) params.get("username");
                    String email = (String) params.get("email");
                    if(StringUtils.isNotBlank(username)){
                        predicates.add(builder.like(root.<String>get("username"), "%" + username + "%"));
                    }
                    if(StringUtils.isNotBlank(email)){
                        predicates.add(builder.equal(root.get("email"), email));
                    }
                    return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
                return builder.conjunction();
            }
        };

        return userDao.findAll(spec, new PageRequest(pageNumber - 1, pageSize, sortBean.genSort()));
    }

    public User find(Integer id) throws AppException {
        User user = userDao.findOne(id);
        if(user == null){
            throw new AppException(MessageCode.USER_NOT_EXIST_ERROR, id);
        }
        return user;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void update(User user) throws AppException {
        User toSave = find(user.getId());

        checkUserName(user);

        toSave.setUsername(user.getUsername());
        toSave.setEmail(user.getEmail());
        toSave.setRole(user.getRole());
        toSave.setUserGroup(user.getUserGroup());
        toSave.setNickname(user.getNickname());
        toSave.setPhone(user.getPhone());
        if(user.getLastLoginDate() != null){
            toSave.setLastLoginDate(user.getLastLoginDate());
        }

        toSave.validate(validator);
        userDao.save(toSave);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Integer id) throws AppException {
        User user = find(id);
        if(user.getDefault()){
            throw new AppException(MessageCode.USER_DELETE_DEFAULT_ERROR);
        }
        userDao.delete(user);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void updatePersonal(User user) throws AppException {
        User entity = find(user.getId());

        checkUserName(user);

        entity.setEmail(user.getEmail());
        entity.setUsername(user.getUsername());
        entity.setNickname(user.getNickname());
        entity.setPhone(user.getPhone());
        userDao.save(entity);
    }

    private void checkUserName(User user) throws AppException {
        User userByName = userDao.findByUsername(user.getUsername());
        if(userByName!=null && !userByName.getId().equals(user.getId())){
            throw new AppException(MessageCode.USER_NAME_EXIST_ERROR, user.getUsername());
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void updatePassword(User user, String newPassword) throws AppException {
        User toSave = find(user.getId());

        String hashPassword = hashPassword(user.getPassword(), toSave.getSalt());
        if(!toSave.getPassword().equals(hashPassword)){
            throw new AppException(MessageCode.USER_OLD_PASSWORD_ERROR);
        }

        toSave.setPassword(hashPassword(newPassword, toSave.getSalt()));
        userDao.save(toSave);
    }

    /**
     * 设定安全的密码，生成随机的salt并经过多次 sha-1 hash
     */
    private void encryptPassword(User user) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        user.setSalt(Hex.encodeHexString(salt));

        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, HASH_ITERATIONS);
        user.setPassword(Hex.encodeHexString(hashPassword));
    }

    private String hashPassword(String password, String salt) throws AppException {
        try {
            return Hex.encodeHexString(Digests.sha1(password.getBytes(), Hex.decodeHex(salt.toCharArray()), HASH_ITERATIONS));
        } catch (DecoderException e) {
            throw new AppException(MessageCode.HEX_DECODE_EXCEPTION_ERROR);
        }
    }
}
