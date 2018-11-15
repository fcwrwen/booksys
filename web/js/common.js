function pagebind(info){
	$(".page-spliter").empty();
	
	var html='';
	//计算上一页
	var prePage=info.currentPage-1;
	if(prePage<=0)
		html+='<span>&lt;</span>';
	else html+='<a href="javascript:loadData('+prePage+')">&lt;</a>' 
	
	//计算首页
	html+='<a href="javascript:loadData(1)">首页</a>';
	
	for(var i=1;i<=info.totalPage;i++){
		if(i==info.currentPage)
			html+='<span class="current">'+i+'</span>';
		else html+='<a href="javascript:loadData('+i+')">'+i+'</a>';
	}
	//html+='<span>...</span>';
	
	//计算尾页
	html+='<a href="javascript:loadData('+info.totalPage+')">尾页</a>';
	
	//计算下一页
	var nextPage=info.currentPage+1;
	if(nextPage>info.totalPage)
		html+='<span>&gt;</span>';
	else html+='<a href="javascript:loadData('+nextPage+')">&gt;</a>';		
	
	$(".page-spliter").append($(html));
}

function logout() {
    $.ajax({
        type:"get",
        url:"logout",
        dataType:"json",
        success:function(data){
            if(data.code === 1){
                alert("注销成功");
                window.location.href="login.html";
            }else if(data.code==-1){
                alert("请先登录");
                window.location.href="login.html";
            }
            else{
                alert(data.msg);
            }
        }
    })
}