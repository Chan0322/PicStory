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

  // 사용자가 입력한 아이디, 비번을 담기 위한 변수
  const [formData, setFormData] = useState({ id: "", pw: "" });

  // 사용자가 입력시(input 태그의 내용이 변경될 때마다) 변수 업데이트
  const handleChange = (e) => {
    // id, pw의 값을 가져옴
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // 로그인 요청 함수
  const handleLoginSubmit = async () => {
    // 빈칸 존재 확인
    if (!formData.id || !formData.pw) {
      alert("아이디와 비밀번호를 입력해주세요!");
      return;
    }
    try {
      // 백엔드에 요청 전달
      const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });
      // 백엔드에서 받은 응답
      const result = await response.text();
      if (result === "로그인 되었습니다.") {
        onLogin(); // 메인 피드로 이동
      } else {
        // 로그인 실패
        alert(result);
      }
    } catch (error) {
      console.error("서버에러: ", error);
      alert("서버 에러가 발생했습니다.");
    }
  };

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
                name="id"
                type="text"
                placeholder="휴대폰 번호, 사용자 이름 또는 이메일"
                onChange={handleChange}
              />
              <input
                name="pw"
                type="password"
                placeholder="비밀번호"
                onChange={handleChange}
              />
              <button
                type="button"
                onClick={handleLoginSubmit}
                className="login-btn"
              >
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
