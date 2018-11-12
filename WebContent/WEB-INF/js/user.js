var userId;
// 添加用户
function addorEditUser() {

	var username = $('#username').val();
	var password = $('#password').val();
	var phone = $('#phone').val();
	var email = $('#email').val();
	if (username.length <= 0) {
		alert("用户名不能为空")
		return;
	}
	if (password.length <= 0) {
		alert("密码不能为空")
		return;
	}
	if (phone.length <= 0) {
		alert("手机号不能为空")
		return;
	}
	var title = $('#myUser').html();
	if (title.indexOf("修改") != -1) {
		var id = $('#id').val();
		var url = "http://localhost:8080/SpringMvc/user/updateUser?username="
				+ username + "&password=" + password + "&phone=" + phone
				+ "&email=" + email + "&userId=" + id;
		$.get(url, function(data) {
			if (data.code == 1) {
				$('#addUser').modal('hide');
				// 刷新界面
				window.location.reload();
			} else if (data.code == 101) {
				// 登录失效
				showLoginDialog();
			} else {
				alert(data.msg);
			}
		});

	} else {
		var url = "http://localhost:8080/SpringMvc/user/addUser?username="
				+ username + "&password=" + password + "&phone=" + phone
				+ "&email=" + email;
		$.get(url, function(data) {
			if (data.code == 1) {
				$('#addUser').modal('hide');
				// 刷新界面
				window.location.reload();
			} else if (data.code == 101) {
				// 登录失效
				showLoginDialog();
			} else {
				alert(data.msg);
			}
		});
	}

}
/**
 * 获取全部用户
 */
function getUser() {
	var url = "http://localhost:8080/SpringMvc/user/getUser";
	$.get(url, function(data) {
		if (data.code == 1) {
			var html = template('user_list', data.data);
			document.getElementById('user_content').innerHTML = html;
		} else if (data.code == 101) {
			// 登录失效
			showLoginDialog();
		} else {
			alert(data.msg)
		}
	});
}

function showDeleteDialog(id) {
	userId = id;
	$('#deleteUser').modal('show');
}

// 删除用户
function deleteUser() {
	if (userId.length <= 0) {
		alert("id不能为空")
		return;
	}
	var url = "http://localhost:8080/SpringMvc/user/deleteUser?userId=" + userId;
	$.get(url, function(data) {
		if (data.code == 1) {
			// 刷新界面
			window.location.reload();
		} else if (data.code == 101) {
			// 登录失效
			showLoginDialog();
		} else {
			alert(data.msg);
		}
	});

}
// 登录弹窗
function showLoginDialog() {
	$('#toLogin').modal('show');
}
// 去登录
function toLogin() {
	window.location = /SpringMvc/;
}

// 添加用户弹窗
function showAddDialog() {
	$('#id').val('');
	$('#username').val('');
	$('#password').val('');
	$('#phone').val('');
	$('#email').val('');

	$('#myUser').html("添加用户");

	$('#addUser').modal('show');

}

// 编辑用户弹窗
function showEditDialog(id) {
	var url = "http://localhost:8080/SpringMvc/user/getUser?userId=" + id;
	$.get(url, function(data) {
		if (data.code == 1) {
			$('#id').val(data.data[0].userId);
			$('#username').val(data.data[0].username);
			$('#password').val(data.data[0].password);
			$('#phone').val(data.data[0].phone);
			$('#email').val(data.data[0].email);

			$('#myUser').html("修改用户");

			$('#addUser').modal('show');
		} else if (data.code == 101) {
			// 登录失效
			showLoginDialog();
		} else {
			alert(data.msg)
		}
	});

}