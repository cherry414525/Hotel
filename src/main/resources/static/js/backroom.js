// 當文件加載完成後執行渲染操作
$(document).ready(function() {
    // 獲取所有房型名稱的函數
        function fetchRoomTypeNames() {
            fetch('/api/types')  // 發送 GET 請求到 /api/rooms/types
                .then(response => response.json())  // 解析 JSON 格式的回應
                .then(roomTypes => {
                    // 清空現有的房型選項
                    $('#roomType').empty();
                    
                     // 添加一個空選項
	                $('#roomType').append($('<option>', {
	                    value: '',
	                    text: '請選擇房型'
	                }));
	                
	                // 將每個房型名稱添加到下拉選單中
	                roomTypes.forEach(roomType => {
	                    $('#roomType').append($('<option>', {
	                        value: roomType.name,
	                        text: roomType.name
	                    }));
	                    $('#addroomType').append($('<option>', {
	                        value: roomType.name,
	                        text: roomType.name
	                    }));
	                    $('#editRoomType').append($('<option>', {
	                        value: roomType.name,
	                        text: roomType.name
	                    }));
	                }); 
	                
                })
                .catch(error => {
                    console.error('取得房型名稱時發生錯誤:', error);
                });
        }

        fetchRoomTypeNames();  // 取得房型名稱
        
     //--所有房間-------------------------------------------------------------   
       // 獲取所有房間資料的函數
    function fetchRooms() {
        fetch('/api/rooms')  // 發送 GET 請求到 /api/rooms
            .then(response => response.json())  // 解析 JSON 格式的回應
            .then(rooms => {
				 console.log('全部:', rooms);
                // 清空現有的房間列表
                $('#room-management tbody').empty();
                
                // 將每個房間資料添加到表格中
                rooms.forEach(room => {
                    var row = '<tr>' +
                                  '<td>' + room.room_id + '</td>' +
                                  '<td>' + room.type_name + '</td>' +
                                  '<td><button class="btn btn-sm btn-primary update-btn" data-room-id="' + room.room_id + '">編輯</button></td>' +
                              	  '<td><button class="btn btn-sm btn-danger delete-btn" data-room-id="' + room.room_id + '">刪除</button></td>' +
                              '</tr>';
                    $('#room-management tbody').append(row);
                });
            })
            .catch(error => {
                console.error('取得房間資料時發生錯誤:', error);
            });
    }

    fetchRooms();  // 取得房間資料
    
    //--搜尋房間--------------------------------------------------------------
    // 搜尋按鈕點擊事件
    $('#searchButton').click(function(e) {
        e.preventDefault(); // 防止默認的表單提交行為
 		console.log('Search button clicked');
        // 獲取房間編號和房型值
        var roomNumber = $('#roomNumber').val();
        var roomType = $('#roomType').val();

        // 建立要發送的數據對象
        var formData = {
            roomNumber: roomNumber,
            roomType: roomType
        };

        // 設置 Fetch 請求的選項
        var requestOptions = {
            method: 'POST', // 使用 POST 方法
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData) // 將數據對象轉為 JSON 字符串
        };
		
		 console.log('Sending fetch request to /api/search');
        // 發送 Fetch 請求
    	fetch('/api/search', requestOptions)
        .then(response => {
            console.log('Response:', response); // 输出整个响应对象，用于调试
            return response.json(); // 解析 JSON 格式的响应
        })
        .then(rooms => {
            console.log('Rooms:', rooms); // 输出成功解析的房间数据，用于确认数据是否正确获取
            // 清空現有的房間列表
            $('#room-management tbody').empty();

            // 將每個房間資料添加到表格中
            rooms.forEach(room => {
                var row = '<tr>' +
                              '<td>' + room.room_id + '</td>' +
                              '<td>' + room.type_name + '</td>' +
                              '<td><button class="btn btn-sm btn-primary update-btn" data-room-id="' + room.room_id + '">編輯</button></td>' +
                              '<td><button class="btn btn-sm btn-danger delete-btn" data-room-id="' + room.room_id + '">刪除</button></td>' +
                          '</tr>';
                $('#room-management tbody').append(row);
            });

        })
        .catch(error => {
            // 處理錯誤情況
            console.error('搜尋失敗:', error);
        });
    });
    
    //--新增房間--------------------------------------------------------------
     $('#addroomButton').on('click', function(e) {
			// 獲取輸入的房間編號和房型
		    var roomNumber = document.getElementById('addRoomNumber').value;
		    var roomType = document.getElementById('addroomType').value;
		
		    // 構建要發送的資料物件
		    var formData = {
		        roomNumber: roomNumber,
		        roomType: roomType
		    };

		    // 發送 POST 請求
		    fetch('/api/addroom', {
		        method: 'POST',
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
		            throw new Error('新增房間失敗');
		        }
		    })
		    .then(message => {
		        // 根據後端返回的訊息來判斷成功或失敗
		        if (message.startsWith('新增房間成功')) {
		            console.log('新增房間成功:', message);
		            alert('新增房間成功！');
		            document.getElementById('addRoomNumber').value = '';
		            document.getElementById('addroomType').value = '';
		             // 關閉模態框
            		$('#addRoomModal').modal('hide');
		            fetchRooms();
		        } else {
		            throw new Error('新增房間失敗');
		        }
		    })
		    .catch(error => {
		        // 處理錯誤情況
		        console.error('新增房間失敗:', error);
		        alert('新增房間失敗！請檢查網絡連接或重試。');
		    });
			
	 });
	 
	 //--刪除房間------------------------------------------------------------
		// 監聽刪除按鈕的點擊事件（事件委派）
	    $('#room-management tbody').on('click', '.delete-btn', function() {
	        var roomId = $(this).data('room-id');
	        console.log('Delete room with ID:', roomId);
	        if (confirm('確定要刪除這個房間嗎？ 請確認此房間並無訂單!')) {
	            fetch('/api/deleteRoom/' + roomId, {
	                method: 'DELETE',
	            })
	                .then(response => {
	                    if (response.ok) {
	                        console.log('房間成功刪除');
	                        fetchRooms();
	                    } else {
	                        throw new Error('刪除房間失敗');
	                    }
	                })
	                .catch(error => {
	                    console.error('刪除房間時發生錯誤:', error);
	                    alert('刪除房間失敗！請檢查是否存在訂單中。');
	                });
	        }
	    });
	 //--修改房間------------------------------------------------------------
	 // 監聽刪除按鈕的點擊事件（事件委派）
	    $('#room-management tbody').on('click', '.update-btn', function() {
			var roomId = $(this).data('room-id');
			// 根據房間ID獲取房間信息
			fetch('/api/room/' + roomId)
			    .then(response => response.json())
			    .then(room => {
			        // 將房間信息填充到修改房間模態框中
			        $('#editRoomNumber').val(room.room_id);
					 $('#editRoomType').val(room.type_name);

			        // 顯示修改房間模態框
			        $('#editRoomModal').modal('show');
			    })
			    .catch(error => {
			        console.error('獲取房間信息失敗:', error);
			        alert('獲取房間信息失敗！請檢查網絡連接或重試。');
			    });
		});
	 	
	 	$('#editRoomButton').on('click', function(e) {
			// 獲取輸入的房間編號和房型
		    var roomNumber = document.getElementById('editRoomNumber').value;
		    var roomType = document.getElementById('editRoomType').value;
			console.log("roomNumber"+roomNumber);
			console.log("roomType"+roomType);
		    // 構建要發送的資料物件
		    var formData = {
		        roomNumber: roomNumber,
		        roomType: roomType
		    };

		    // 發送 POST 請求
        	fetch('/api/updateRoom', {
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
                    throw new Error('修改房間失敗');
                }
            })
            .then(message => {
                // 根據後端返回的訊息來判斷成功或失敗
                if (message.startsWith('修改房間成功')) {
                    console.log('修改房間成功:', message);
                    alert('修改房間成功！');
                    $('#editRoomModal').modal('hide'); // 關閉模態框
                    fetchRooms(); // 重新加載房間資料
                } else {
                    throw new Error('修改房間失敗');
                }
            })
            .catch(error => {
                // 處理錯誤情況
                console.error('修改房間失敗:', error);
                alert('修改房間失敗！請檢查網絡連接或重試。');
            });
			
	 	});
	 
});