// 當文件加載完成後執行渲染操作
$(document).ready(function() {
	//判斷是否登入
		 function checkSession() {
	    fetch('/api/checkSession')
	        .then(response => response.json())
	        .then(data => {
	            if (data === true) {
	                console.log('使用者已登錄');
	                // 執行使用者已登錄時的相應操作
	            } else {
	                console.log('使用者未登錄');
	                 window.location.href = 'backlogin.html';
	                // 執行使用者未登錄時的相應操作
	            }
	        })
	        .catch(error => {
	            console.error('檢查會話時發生錯誤:', error);
	        });
		}
	
	checkSession();
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
    	// 監聽刪除按鈕的點擊事件（事件委派）
	    $('#user-table tbody').on('click', '.update-btn', function() {
			var userId = $(this).data('user-id');
			// 根據會員ID獲取會員資料
			fetch('/api/user/' + userId)
			    .then(response => response.json())
			    .then(user => {
			        // 將房間信息填充到修改房間模態框中
			         $('#UserId').val(user.user_id);
					 $('#UserName').val(user.name);
					 $('#UserBirthday').val(user.birthday);
					 $('#UserGender').val(user.gender);
					 $('#UserPhone').val(user.phone);
					 $('#UserEmail').val(user.email);

			        // 顯示修改會員模態框
			        $('#editUserModal').modal('show');
			    })
			    .catch(error => {
			        console.error('獲取會員失敗:', error);
			        alert('獲取會員資料失敗！請檢查網絡連接或重試。');
			    });
		});
    
    	// 監聽修改會員按鈕的點擊事件
		$('#editUserButton').on('click', function(e) {
		    // 獲取輸入的會員資料
		    var userId = document.getElementById('UserId').value;
		    var userName = document.getElementById('UserName').value;
		    var userBirthday = document.getElementById('UserBirthday').value;
		    var userGender = document.getElementById('UserGender').value;
		    var userPhone = document.getElementById('UserPhone').value;
		    var userEmail = document.getElementById('UserEmail').value;
		
		    // 構建要發送的資料物件
		    var formData = {
		        user_id: userId,
		        name: userName,
		        birthday: userBirthday,
		        gender: userGender,
		        phone: userPhone,
		        email: userEmail
		    };
		
		    // 發送 PUT 請求更新會員資料
		    fetch('/api/updateUser', {
		            method: 'PUT',
		            headers: {
		                'Content-Type': 'application/json'
		            },
		            body: JSON.stringify(formData) // 將資料物件轉為 JSON 字串
		        })
		        .then(response => {
		            if (response.ok) {
		                // 如果响應狀態碼為 200-299，解析 JSON 數據
		                return response.text(); // 返回文本數據
		            } else {
		                // 如果响應狀態碼不在 200-299 範圍內，拋出錯誤
		                throw new Error('修改會員失敗');
		            }
		        })
		        .then(message => {
		            // 根據後端返回的訊息來判斷成功或失敗
		            if (message.startsWith('修改會員成功')) {
		                console.log('修改會員成功:', message);
		                alert('修改會員成功！');
		                $('#editUserModal').modal('hide'); // 關閉模態框
		                fetchMembers(); // 重新加載會員資料
		            } else {
		                throw new Error('修改會員失敗');
		            }
		        })
		        .catch(error => {
		            // 處理錯誤情況
		            console.error('修改會員失敗:', error);
		            alert('修改會員失敗！請檢查網絡連接或重試。');
		        });
		});
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