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
                                  '<td><button class="btn btn-sm btn-primary update-btn" data-user-id="' + user.user_id + '">編輯</button></td>' +
                                  '<td><button class="btn btn-sm btn-danger delete-btn" data-user-id="' + user.user_id + '">刪除</button></td>' +
                              '</tr>';
                    $('#user-table tbody').append(row);
                });
            })
            .catch(error => {
                console.error('取得會員資料時發生錯誤:', error);
            });
    }

    fetchMembers();  // 取得會員資料
    //--搜尋會員------------------------------------------------------------
    	// 搜尋按鈕點擊事件
		$('#searchButton').click(function(e) {
		    e.preventDefault(); // 防止默認的表單提交行為
		    console.log('搜尋按鈕點擊');
		
		    // 獲取會員編號和姓名值
		    var userId = $('#userNumber').val();
		    var userName = $('#userName').val();
		
		    // 建立要發送的數據對象
		    var formData = {
		        userId: userId,
		        userName: userName
		    };
		
		    // 設置 Fetch 請求的選項
		    var requestOptions = {
		        method: 'POST', // 使用 POST 方法
		        headers: {
		            'Content-Type': 'application/json'
		        },
		        body: JSON.stringify(formData) // 將數據對象轉為 JSON 字符串
		    };
		
		    console.log('發送 Fetch 請求到 /api/search-members');
		    // 發送 Fetch 請求
		    fetch('/api/searchusers', requestOptions)
		    .then(response => {
		        console.log('響應:', response); // 輸出整個響應對象，用於調試
		        return response.json(); // 解析 JSON 格式的響應
		    })
		    .then(users => {
		        console.log('會員:', users); // 輸出成功解析的會員數據，用於確認數據是否正確獲取
		        // 清空現有的會員列表
		        $('#user-management tbody').empty();
		
		        
		        // 將每個會員資料添加到表格中
                users.forEach(user => {
                    var row = '<tr>' +
                                  '<td>' + user.user_id+ '</td>' +
                                  '<td>' + user.name + '</td>' +
                                  '<td>' + user.birthday + '</td>' +
                                  '<td>' + user.gender + '</td>' +
                                  '<td>' + user.phone + '</td>' +
                                  '<td>' + user.email + '</td>' +
                                  '<td><button class="btn btn-sm btn-primary update-btn" data-user-id="' + user.user_id + '">編輯</button></td>' +
                                  '<td><button class="btn btn-sm btn-danger delete-btn" data-user-id="' + user.user_id + '">刪除</button></td>' +
                              '</tr>';
                    $('#user-table tbody').append(row);
                });
		
		    })
		    .catch(error => {
		        // 處理錯誤情況
		        console.error('搜尋失敗:', error);
		    });
		});

    
    //--修改會員------------------------------------------------------------
    //--刪除會員------------------------------------------------------------
    	// 監聽刪除按鈕的點擊事件（事件委派）
		$('#user-management tbody').on('click', '.delete-btn', function() {
		    var userId = $(this).data('user-id');
		    console.log('刪除會員 ID:', userId);
		    if (confirm('確定要刪除這個會員嗎？ 將刪除所有此會員的訂單!!!')) {
		        fetch('/api/deleteUser/' + userId, {
		            method: 'DELETE',
		        })
		        .then(response => {
		            if (response.ok) {
		                console.log('會員成功刪除');
		                fetchMembers(); // 重新加載會員列表的函數，用於更新 UI
		            } else {
		                throw new Error('刪除會員失敗');
		            }
		        })
		        .catch(error => {
		            console.error('刪除會員時發生錯誤:', error);
		            alert('刪除會員失敗！');
		        });
		    }
		});
});