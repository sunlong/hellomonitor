/**
 * User: sunlong
 * Date: 13-3-28
 * Time: 下午3:29
 */
function AjaxTable(data){
    this.theData = null;
    this.param = data.param;
    this.url = data.url;
    this.errorDiv = data.errorDiv;
    this.container = data.container;
    this.columns = data.columns;
    this.hasAction = false;
    this.id = new Date().getTime();
    this.actions = data.actions;
}

AjaxTable.prototype.getData = function(){
    return this.theData;
};

AjaxTable.prototype.genHeader = function(){
    var i= 0, tHead = '';
    for(; i<this.columns.length; i++){
        tHead += '<th>'+ this.columns[i].title +'</th>';
    }
    tHead += '<th class="hideOperation'+ this.id +'">操作</th>';
    return tHead;
};

AjaxTable.prototype.genBody = function(data){
    var body = '';
    for(var i=0; i<data.length; i++){
        body += '<tr>';
            for(var j=0; j<this.columns.length; j++){
                if(this.columns[j].render){
                    body += '<td>'+ this.columns[j].render(i) +'</td>';
                }else{
                    body += '<td>'+ data[i][this.columns[j].field] +'</td>'
                }
            }
            body += this.genActions(i);
        body += '</tr>';
    }
    return body;
};

AjaxTable.prototype.genActions = function(index){
    var html = '<td class="hideOperation'+ this.id +'">';
    var actions = this.actions || [];
    for(var i=0; i< actions.length; i++){
        if( !actions[i].beforeRender || (actions[i].beforeRender && actions[i].beforeRender(index)) ) {
            this.hasAction = true;
            html += '<a href="javascript:void(0)" class="btn" data-action="'+ i +','+ index +'">'+ actions[i].text +'</a>&nbsp;&nbsp;';
        }
    }
    html += '</td>';
    return html;
};

AjaxTable.prototype.genFooter = function(data){
    var paginationTemplate =
        '<div class="pagination pagination-centered"><ul>'+
            '<# if(data.number > 0){#>'+
            '<li class="disabled"><a href="javascript:void(0);" data-pagination="<#= data.number #>">&lt; </a></li>'+
            '<# }else{ #>'+
            '<li class="disabled"><a href="javascript:void(0)">&lt;</a></li>'+
            '<# } #>'+

            '<li class="active"><a href="javascript:void(0);" data-pagination="<#= data.number+1 #>"><#= data.number+1 #></a></li>' +

            '<# if(data.numberOfElements == data.size){#>'+
            '<li class="disabled"><a href="javascript:void(0);" data-pagination="<#= data.number+2 #>">&gt; </a></li>'+
            '<# }else{ #>'+
            '<li class="disabled"><a href="javascript:void(0)">&gt;</a></li>'+
            '<# } #>'+
            '</ul></div>';
    return parseTemplate(paginationTemplate, {data: data});
};

AjaxTable.prototype.pagination = function(page){
    this.param.page = page;
    var _this = this;
    $.get(this.url, this.param, function(data){
        if(data.success){
            _this.theData = data.data.content;
            var tableId = 'table'+ _this.id;
            var tableHtml = '<table id="'+ tableId +'" class="table table-striped table-bordered table-condensed"><thead>'+ _this.genHeader() +'</thead><tbody></tbody></table>';

            var body = _this.genBody(data.data.content);
            //是否显示操作栏
            if(_this.hasAction){
                tableHtml = '<style type="text/css">.hideOperation'+ _this.id +'{display: table-cell;}</style>' + tableHtml;
            }else{
                tableHtml = '<style type="text/css">.hideOperation'+ _this.id +'{display: none;}</style>' + tableHtml;
            }

            $(_this.container).html('');
            $(_this.container).append(tableHtml);

            $("#"+ tableId +" tbody").html(body);
            $(_this.container).append(_this.genFooter(data.data));
            //附加动作
            $('a[data-action]').click(function(){
                var params = $(this).attr('data-action').split(',');
                _this.actions[ params[0] ].handler(this, params[1]);//执行handler
            });
            $('a[data-pagination]').click(function(){
                var page = $(this).attr('data-pagination');
                _this.pagination(page);
            });
        }else{
            common.showError(_this.errorDiv, data.data);
        }
    });
};