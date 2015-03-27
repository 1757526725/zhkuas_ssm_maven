function check(){
	var ipt_username=$("input[name='username']");
	var ipt_passowrd=$("input[name='password']");
	var error_summary=$(".error-summary");
	var ul=$(".error-summary > ul");
	if(ipt_username.val()==null||ipt_username.val().length==0){
		ul.empty();
		ul.append("<li>用户名不能为空！</li>");
		error_summary.toggle();
		return (false);
	}else if(ipt_passowrd.val()==null||ipt_passowrd.val().length==0){
		ul.empty();
		ul.append("<li>密码不能为空！</li>");
		error_summary.toggle();
		return (false);
	}
	return true;
};
$(document).ready(function() {

    if ($.browser.msie == true && $.browser.version.slice(0, 3) < 10) {
        $('input[placeholder]').each(function() {

            var input = $(this);

            $(input).val(input.attr('placeholder'));

            $(input).focus(function() {
                if (input.val() == input.attr('placeholder')) {
                    input.val('');
                }
            });

            $(input).blur(function() {
                if (input.val() == '' || input.val() == input.attr('placeholder')) {
                    input.val(input.attr('placeholder'));
                }
            });
        });

    }
    
    $("a[type='submit']").click(function(){
    	$("#loginform").submit();
    });
});

