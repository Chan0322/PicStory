import React from "react";
import "./LoginPage.css";
import loginImg from "../assets/LoginImage.jpg";

function LoginPage({ onLogin }) {
  return (
    <div className="login-container">
      <div className="login-content">
        {/* 왼쪽 이미지 */}
        <div className="login-image-section">
          <img src={loginImg} alt="loginframe" style={{ height: "580px" }} />
        </div>

        {/* 오른쪽 로그인,회원가입 */}
        <div className="login-form-section">
          <div className="form-box">
            <h1 className="logo">PicStory</h1>

            <form className="login-form">
              <input
                type="text"
                placeholder="휴대폰 번호, 사용자 이름 또는 이메일"
              />
              <input type="password" placeholder="비밀번호" />
              <button type="button" onClick={onLogin} className="login-btn">
                로그인
              </button>
            </form>

            <div className="divider">
              <div className="line"></div>
              <div className="text">또는</div>
              <div className="line"></div>
            </div>

            <div className="facebook-login">
              <span>Facebook으로 로그인</span>
            </div>
          </div>

          <div className="signup-box">
            <p>
              계정이 없으신가요?{" "}
              <span className="signup-link">가입하기개발중입니다</span>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
