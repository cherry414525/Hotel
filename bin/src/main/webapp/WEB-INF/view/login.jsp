<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

            h1 {
                font-weight: bolder;
                font-feature-settings: "smcp", "zero";
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
                            <div id="loginForm">
                                <h2 class="text-center">會員登入</h2>
                                <form action="/login" method="post">
                                    <div class="form-group">
                                        <label for="loginEmail">Email 地址</label><small style="color:red">
                                            ${error}</small>
                                        <input type="email" class="form-control" id="loginEmail" name="email"
                                            aria-describedby="emailHelp" placeholder="請輸入 Email">
                                    </div>
                                    <div class="form-group">
                                        <label for="loginPassword">密碼</label>
                                        <input type="password" class="form-control" id="loginPassword" name="password"
                                            placeholder="請輸入密碼">
                                    </div>
                                    
                                    <button type="submit" class="btn btn-primary btn-block">登入</button>
                                </form>
                                <a href="/register" class="btn btn-info btn-block mt-3">註冊</a>

                            </div>
                        </div>
                    </div>
                </div>
            </main>
            <footer class="bg-light">
                <div class="container ">
                    <h3>Starry Hotel</h3>
                    <hr>
                    <p>
                    <div><img src="../hotel_img/icon-map.webp" style="width: 1em; height: 1em;"> 地址: 10491台北市中山區民生東路三段
                    </div>
                    <p>
                    <div><img src="../hotel_img/icon-mail.webp" style="width: 1em; height: 1em;"> Email:
                        Starry1234@gmail.com</div>
                    <p>
                    <div><img src="../hotel_img/icon-phone.webp" style="width: 1em; height: 1em;"> 電話: 0987654321</div>
                    <p>
                </div>
            </footer>
        </div>

        <!-- Forgot Password Modal -->
        <div class="modal fade" id="forgotPasswordModal" tabindex="-1" role="dialog"
            aria-labelledby="forgotPasswordModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="forgotPasswordModalLabel">忘記密碼</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form action="/forgotPassword" method="GET">
                            <div class="form-group">
                                <label for="forgotPasswordEmail">Email 地址</label>
                                <input type="email" class="form-control" id="forgotPasswordEmail" name="email"
                                    placeholder="輸入註冊時使用的 Email">
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                                <button type="submit" class="btn btn-primary">寄送</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
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