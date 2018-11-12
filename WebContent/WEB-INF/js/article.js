var articleId;

// 删除文章
function deleteArticle() {
	if (articleId.length <= 0) {
		alert("id不能为空")
		return;
	}
	var url = "http://localhost:8080/SpringMvc/article/deleteArticle?articleId="
			+ articleId;
	$.get(url, function(data) {
		if (data.code == 1) {
			// 刷新界面
			showArticle();
		} else {
			alert(data.msg);
		}
	});

}

function showDeleteArttcleDialog(id) {
	articleId = id;
	$('#deleteArticle').modal('show');
}

// 编辑文章弹窗
function showEditArticleDialog(id) {
	var url = "http://localhost:8080/SpringMvc/article/getArticle?articleId=" + id;
	$.get(url, function(data) {
		if (data.code == 1) {
			$('#id').val(data.data[0].articleId);
			$('#article_title').val(data.data[0].title);
			$('#article_link').val(data.data[0].link);

			$('#articleTitle').html("修改文章");

			$('#addArticle').modal('show');
		} else {
			alert(data.msg)
		}
	});

}

// 显示文章
function showArticle() {
	var url = "http://localhost:8080/SpringMvc/article/getArticle";
	$.get(url, function(data) {
		if (data.code == 1) {
			var html = template('article_list', data.data);
			document.getElementById('article_content').innerHTML = html;
		} else {
			alert(data.msg)
		}
	});
}
// 添加文章弹窗
function showAddArticleDialog() {
	$('#id').val('');
	$('#article_title').val('');
	$('#article_link').val('');

	$('#articleTitle').html("添加文章");
	$('#addArticle').modal('show');

}

// 添加/修改文章
function addorEditArticle() {

	var article_title = $('#article_title').val();
	var article_link = $('#article_link').val();
	if (article_title.length <= 0) {
		alert("标题不能为空")
		return;
	}
	if (article_link.length <= 0) {
		alert("链接不能为空")
		return;
	}
	var title = $('#articleTitle').html();
	if (title.indexOf("修改") != -1) {
		var id = $('#id').val();
		var url = "http://localhost:8080/SpringMvc/article/updateArticle";
		var obj = {
			'title' : article_title,
			'link' : article_link,
			'articleId' : articleId
		};

		$.post(url, obj, function(data) {
			if (data.code == 1) {
				$('#addArticle').modal('hide');
				// 刷新界面
				showArticle();
			} else {
				alert(data.msg);
			}
		});

	} else {
		var url = "http://localhost:8080/SpringMvc/article/addArticle";
		var obj = {
			'title' : article_title,
			'link' : article_link
		};
		$.post(url, obj, function(data) {
			if (data.code == 1) {
				$('#addArticle').modal('hide');
				// 刷新界面
				showArticle();
			} else {
				alert(data.msg);
			}
		});
	}

}