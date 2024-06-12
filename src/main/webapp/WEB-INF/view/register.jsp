<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

        /* navbar*/
        nav.navbar {
            /* 陰影效果 */
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
            /* 確保 nav 區塊在最上層 */
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            z-index: 1000;
            font-weight: bold;
        }

        /* main */
        body {
            /*background-color: #FFFEFB;*/
            font-family: Arial, sans-serif;
        }

        .wrapper {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        /* 調整主要內容區域 */
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
                        <h1> ABC Hotel</h1>
                    </a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav ml-auto">
                            <li class="nav-item">
                                <a class="nav-link" href="/hotel">首頁</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">會員中心</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
        <!-- Main Content Area -->
        <main class="main-content align-content-center" style="margin: 85px;">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-6 ">
                       
                        <div id="registerForm" >
                            <h2 class="text-center">會員註冊</h2>
                            <form method="POST" action="/register">
                                <div class="form-group">
                                    <label for="name">姓名</label>
                                    <input type="text" class="form-control" id="name" name="name" placeholder="輸入姓名" value="${userDto.name }">
                                </div>
                                <div class="form-group">
                                    <label for="registerEmail">Email</label> <small style="color:red"> ${error1}</small>
                                    <input type="email" class="form-control" id="email" name="email"
                                        aria-describedby="emailHelp" placeholder="輸入Email" value="${userDto.email }">
                                </div>
                                <div class="form-group">
                                    <label for="birthday">生日</label>
                                    <input type="date" class="form-control" id="birthday" name="birthday" value="${userDto.birthday }">
                                </div>
                                <div class="form-group">
                                    <label for="gender">性別</label>
                                    <select class="form-control" id="gender" name="gender" value="${userDto.gender }">
                                        <option value="male">男性</option>
                                        <option value="female">女性</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="phone">電話</label>
                                    <input type="tel" class="form-control" id="phone" name="phone" placeholder="輸入電話" value="${userDto.phone }">
                                </div>
                                <div class="form-group">
                                    <label for="password">密碼</label><small style="color:red"> ${error2}</small>
                                    <input type="password" class="form-control" id="password" name="password"
                                        placeholder="輸入密碼">
                                </div>
                                <div class="form-group">
                                    <label for="confirmPassword">確認密碼</label>
                                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                                        placeholder="再次輸入密碼">
                                </div>
                                <button type="submit" class="btn btn-success btn-block">會員註冊</button>
                            </form>
                            <a href="/login" class="btn btn-info btn-block mt-3">登入</a>
                            
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <footer class="bg-light">
            <div class="container ">
                <h3>ABC Hotel</h3>
                <hr>
                <p>
                <div><img src="../hotel_img/icon-map.webp" style="width: 1em; height: 1em;"> 地址: 10491台北市中山區民生東路三段
                </div>
                <p>
                <div><img src="../hotel_img/icon-mail.webp" style="width: 1em; height: 1em;"> Email:
                    abcdefg1234@gmail.com</div>
                <p>
                <div><img src="../hotel_img/icon-phone.webp" style="width: 1em; height: 1em;"> 電話: 0987654321</div>
                <p>
            </div>
        </footer>
    </div>

    <script>
        $(document).ready(function () {
            $('.navbar-nav>li>a').on('click', function () {
                $('.navbar-collapse').collapse('hide');
            });
        });

       

    </script>
</body>
</html>