layui.use(['layer', 'jquery'], function(){
  var layer = layui.layer
  ,$ = layui.jquery;
  
  $('body').on('mousedown','.layui-form-checkbox',function(e){
	  var event= e||window.event;
	  if(event.button==2){ //鼠标右击
		  console.log('right');
	  }else if(event.button==0){//鼠标左击
		  console.log('left');

		  if($(this).parents('.layui-table-body').hasClass('layui-table-main')){ //单选
			 if(!$(this).hasClass('layui-unselect layui-form-checkbox layui-form-checked')){
				 $(this).parents('tr').addClass('tablebluebg');
			 }else{
				 $(this).parents('tr').removeClass('tablebluebg'); 
			 }
		  }else{
			  if(!$(this).hasClass('layui-unselect layui-form-checkbox layui-form-checked')){
				  $(this).parents('.layui-table-header').next().find('tr').addClass('tablebluebg');
			  }else{
				  $(this).parents('.layui-table-header').next().find('tr').removeClass('tablebluebg'); 
			  }
		  }
	  }
  })
 
});  