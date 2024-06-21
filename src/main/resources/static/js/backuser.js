// 當文件加載完成後執行渲染操作
$(document).ready(function() {
	
 	// 獲取所有會員資料的函數
    function fetchMembers() {
        fetch('/api/members')  // 發送 GET 請求到 /api/members，這裡假設後端提供了這個 API
            .then(response => response.json())  // 解析 JSON 格式的回應
            .then(users => {
                console.log('全部會員:', users);
                // 清空現有的會員列表
                $('#user-table tbody').empty();
                
                // 將每個會員資料添加到表格中
                users.forEach(user => {
                    var row = '<tr>' +
                                  '<td>' + user.user_id+ '</td>' +
                                  '<td>' + user.name + '</td>' +
                                  '<td>' + user.birthday + '</td>' +
                                  '<td>' + user.gender + '</td>' +
                                  '<td>' + user.phone + '</td>' +
                                  '<td>' + user.email + '</td>' +
                                  '<td><button class="btn btn-sm btn-primary update-btn" data-member-id="' + user.user_id + '">編輯</button></td>' +
                                  '<td><button class="btn btn-sm btn-danger delete-btn" data-member-id="' + user.user_id + '">刪除</button></td>' +
                              '</tr>';
                    $('#user-table tbody').append(row);
                });
            })
            .catch(error => {
                console.error('取得會員資料時發生錯誤:', error);
            });
    }

    fetchMembers();  // 取得會員資料
    
    //--修改會員------------------------------------------------------------
});