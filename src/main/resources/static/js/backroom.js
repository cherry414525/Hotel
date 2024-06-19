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
	                }); 
                })
                .catch(error => {
                    console.error('取得房型名稱時發生錯誤:', error);
                });
        }

        fetchRoomTypeNames();  // 取得房型名稱
        
        
       // 獲取所有房間資料的函數
    function fetchRooms() {
        fetch('/api/rooms')  // 發送 GET 請求到 /api/rooms
            .then(response => response.json())  // 解析 JSON 格式的回應
            .then(rooms => {
                // 清空現有的房間列表
                $('#room-management tbody').empty();
                
                // 將每個房間資料添加到表格中
                rooms.forEach(room => {
                    var row = '<tr>' +
                                  '<td>' + room.room_id + '</td>' +
                                  '<td>' + room.type_name + '</td>' +
                                  '<td><button class="btn btn-sm btn-primary">編輯</button></td>' +
                                  '<td><button class="btn btn-sm btn-danger">刪除</button></td>' +
                              '</tr>';
                    $('#room-management tbody').append(row);
                });
            })
            .catch(error => {
                console.error('取得房間資料時發生錯誤:', error);
            });
    }

    fetchRooms();  // 取得房間資料
});