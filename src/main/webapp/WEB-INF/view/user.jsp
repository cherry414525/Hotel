<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>Hotel</title>
      <link rel="icon" href="../hotel_img/icon-hotel.webp">
      <!-- Bootstrap CSS -->
      <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
      <!-- Bootstrap JS and jQuery -->
      <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
      <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
      <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
      <!-- Custom CSS -->
      <style>
        * {
          font-family: Georgia, serif;
        }

        /* navbar */
        nav.navbar {
          box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
          position: fixed;
          top: 0;
          left: 0;
          width: 100%;
          z-index: 1000;
          font-weight: bold;
        }

        /* main */
        body {
          font-family: Arial, sans-serif;
          min-height: 80vh;
          display: flex;
          flex-direction: column;
        }

        .wrapper {
          min-height: 80vh;
          display: flex;
          flex-direction: column;
          flex: 1;
        }

        /* Adjust main content area */
        .main-content {
          flex: 1;
        }
      </style>
    </head>

    <body>
      <div class="wrapper">
        <header>
          <!-- Navigation -->
          <nav class="navbar navbar-expand-lg  navbar-light bg-light">
            <div class="container">
              <a class="navbar-brand" href="#">
                <h1> Starry Hotel</h1>
              </a>
              <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
              </button>

              <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto">
                  <li class="nav-item">
                    <a class="nav-link" href="/hotel">首頁</a>
                  </li>

                  <!-- Using Bootstrap Dropdown component -->
                  <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                      data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      會員中心
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                      <a class="dropdown-item" href="/member">我的訂單</a>
                      <a class="dropdown-item" href="/member/user">我的資料</a>
                    </div>
                  </li>

                  <li class="nav-item">
                    <a class="nav-link" href="/logout">登出</a>
                  </li>
                </ul>
              </div>
            </div>
          </nav>
        </header>

        <main class="main-content align-content-center" style="margin: 85px;">
          <div class="container">
            <div class="row justify-content-center">
              <div class="col-md-6 ">

                <!-- 會員資料卡片 -->
                <div class="card border-primary">
                  <div class="card-body">
                    <h2 class="text-center">會員資料</h2>
                    <form id="memberForm" method="POST" action="/member/user/update">
                      <input type="hidden" name="_method" value="PUT">
                      <input type="hidden" name="user_id" value="${user.user_id}">
                      <div class="form-group">
                        <label for="name">姓名</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="輸入姓名"
                          value="${user.name }">
                      </div>
                      <div class="form-group">
                        <label for="registerEmail">Email</label> 
                        <input type="email" class="form-control" id="email" name="email" aria-describedby="emailHelp"
                          placeholder="輸入Email" value="${user.email }" readonly>
                      </div>
                      <div class="form-group">
                        <label for="birthday">生日</label>
                        <input type="date" class="form-control" id="birthday" name="birthday" value="${birthday}">
                      </div>
                      <div class="form-group">
                        <label for="gender">性別</label>
                        <select class="form-control" id="gender" name="gender">
                          <option value="male" id="maleOption">男性</option>
                          <option value="female" id="femaleOption">女性</option>
                        </select>
                      </div>
                      <div class="form-group">
                        <label for="phone">電話</label>
                        <input type="tel" class="form-control" id="phone" name="phone" placeholder="輸入電話"
                          value="${user.phone }">
                      </div>

                      <button type="submit" id="editButton" class="btn btn-success btn-block">修改會員資料</button>
                    </form>
                  </div>
                </div>

                <!-- 修改密碼卡片 -->
                <div class="card border-secondary mt-4">
                  <div class="card-body">
                    <form id="passwordForm" method="POST" action="/member/updatepassword" style="display: none;">
                      <input type="hidden" name="_method" value="PUT">
                      <input type="hidden" name="user_id" value="${user.user_id}">
                      <h2 class="text-center">修改密碼</h2>
                      <div class="form-group">
                        <label for="newPassword">新密碼</label><small style="color:red"> ${sessionScope.error2}</small>
                        <input type="password" class="form-control" id="newPassword" name="newPassword"
                          placeholder="輸入新密碼">
                      </div>
                      <div class="form-group">
                        <label for="confirmPassword">確認新密碼</label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                          placeholder="確認新密碼">
                      </div>
                      <button type="submit" id="hiddenSubmitButton" style="display: none;"></button>
                    </form>
                    <div class="mt-3">
                      <button type="button" id="newPasswordButton" class="btn btn-primary btn-block">修改密碼</button>
                      <a href="/member" class="btn btn-secondary btn-block mt-2">回上頁</a>
                    </div>
                  </div>
                </div>


              </div>
            </div>
          </div>
      </div>
      </div>
      </main>


      <footer class="bg-light">
        <div class="container">
          <h3>Starry Hotel</h3>
          <hr>
          <p>
          <div><img src="../hotel_img/icon-map.webp" style="width: 1em; height: 1em;"> 地址: 10491台北市中山區民生東路三段
          </div>
          <p>
          <div><img src="../hotel_img/icon-mail.webp" style="width: 1em; height: 1em;"> Email: Starry1234@gmail.com
          </div>
          <p>
          <div><img src="../hotel_img/icon-phone.webp" style="width: 1em; height: 1em;"> 電話: 0987654321</div>
          <p>
        </div>
      </footer>
      </div>

      <script>
        $(document).ready(function () {
          $('.navbar-nav>li>a').on('click', function (e) {
            if (!$(e.target).hasClass('dropdown-toggle')) {
              $('.navbar-collapse').collapse('hide');
            }
          });

          

          // 假設後端傳遞的 gender 是 'female'，希望預選女性選項
          var gender = "${gender}";
          if (gender === 'female') {
            document.getElementById('femaleOption').selected = true;
          } else {
            document.getElementById('maleOption').selected = true;
          }


          // 初始狀態，所有輸入框設為唯讀
          $('input, select').prop('readonly', true);
          // 鎖定性別不能修改
          $('#gender').prop('disabled', true); // 將性別下拉列表設為禁用

          // 點擊按鈕切換到編輯模式
          $('#editButton').on('click', function () {
            event.preventDefault(); // 阻止按鈕的默認行為，即提交表單

            // 切換到編輯模式
            if ($(this).text().trim() === '修改會員資料') {
              $('input, select').prop('readonly', false);
              $('#gender').prop('disabled', false); // 啟用下拉列表
              $(this).text('確認修改');
            } else {
              // 提交表單
               $('#memberForm').submit();
              alert('修改完成');
            }
          });

          $('#newPasswordButton').on('click', function () {
            event.preventDefault(); // 阻止按鈕的默認行為，即提交表單

            // 切換到編輯模式
            if ($(this).text().trim() === '修改密碼') {
              $('#passwordForm').show();
              $('#newPassword, #confirmPassword').prop('readonly', false);
              $(this).text('確認修改密碼');
            } else {
              // 提交表單
              $('#hiddenSubmitButton').click();
              
              
            }
          });

        });
      </script>

    </body>

    </html>