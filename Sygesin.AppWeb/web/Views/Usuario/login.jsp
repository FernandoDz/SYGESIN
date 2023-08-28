<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css">
        <style>
            body {
                display: flex;
                min-height: 100vh;
                flex-direction: column;
            }
            main {
                flex: 1 0 auto;
            }
            body {
                background: #fff;
            }

            .input-field input[type=date]:focus + label,
            .input-field input[type=text]:focus + label,
            .input-field input[type=email]:focus + label,
            .input-field input[type=password]:focus + label {
                color: #e91e63;
            }

            .input-field input[type=date]:focus,
            .input-field input[type=text]:focus,
            .input-field input[type=email]:focus,
            .input-field input[type=password]:focus {
                border-bottom: 2px solid #e91e63;
                box-shadow: none;
            }
        </style>
    </head>
    <body>
        <div class="section"></div>
        <main>
            <center>
                <div class="section"></div>
                <div class="section"></div>

                <div class="container">
                    <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">     
                    <div class="z-depth-1 grey lighten-4 row" style="display: inline-block; padding: 32px 48px 0px 48px; border: 1px solid #EEE;">
                        <label class="flow-text">Bienvenido</label>
                        <form class="col s12" action="Usuario?accion=login" method="post">
                            <div class='row'>
                                <div class='col s12'>
                                </div>
                            </div>

                            <div class='row'>
                                <div class='input-field col s12'>
                                    <input  id="txtLogin" type="text" name="login" required class="validate" maxlength="25">  
                                    <label for='email'>Login</label>
                                </div>
                            </div>

                            <div class='row'>
                                <div class='input-field col s12'>
                                    <input  id="txtPassword" type="password" name="password" required class="validate" minlength="5" maxlength="32">
                                    <label for='password'>Contraseña</label>
                                </div>
                                <label style='float: right;'>
                                    <a class='pink-text' href='#!'><b>Forgot Password?</b></a>
                                </label>
                            </div>
                            <br />
                            <center>
                                <div class='row'>
                                    <button type='submit'  class='col s12 btn btn-large waves-effect indigo'>Login</button>
                                </div>
                                <% if (request.getAttribute("error") != null) { %>
                                <div class="row">
                                    <div class="col l12 s12">
                                        <span style="color:red"><%= request.getAttribute("error") %></span>                                              
                                    </div>
                                </div>
                                <%}%>
                            </center>
                        </form>
                    </div>
                </div>
            </center>
            <div class="section"></div>
            <div class="section"></div>
        </main>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>
    </body>
</html>