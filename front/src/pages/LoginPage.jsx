import React, { useEffect, useState } from "react";
import "./LoginPage.css";
import loginImg from "../assets/LoginImage.jpg";

function LoginPage({ onLogin, onShowSignup }) {
  const [connectionMessage, setConnectionMessage] = useState("연결 확인 중..");

  useEffect(() => {
    // 백엔드 API 호출
    fetch("http://localhost:8080/api/test")
      .then((response) => response.text())
      .then((data) => {
        setConnectionMessage(data);
      })
      .catch((error) => {
        console.error("Error:", error);
        setConnectionMessage("서버가 꺼져있습니다.");
      });
  }, []);

  return (
    <div className="login-container">
      {/* 백엔드와 연결 테스트 메시지 */}
      <div
        style={{
          position: "fixed",
          bottom: "20px",
          color: "red",
          fontSize: "20px",
        }}
      >
        서버 상태 : {connectionMessage}
      </div>
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
              <span className="link" onClick={onShowSignup}>
                가입하기
              </span>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
