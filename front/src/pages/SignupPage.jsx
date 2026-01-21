import React, { useState } from "react";
import "./LoginPage.css";

function SignupPage({ onShowLogin }) {
  // 사용자의 입력 정보 저장 변수
  const [formData, setFormData] = useState({
    id: "", // 휴대폰 or 이메일
    pw: "",
    userName: "",
    nickname: "",
  });

  // input 태그의 내용이 바뀔때마다 변수 업데이트
  const handleChange = (e) => {
    // 타겟에서 입력된 input 태그 정보와 입력 내용을 가져와
    const { name, value } = e.target;
    // 변수 업데이트
    setFormData({ ...formData, [name]: value });
  };

  // 가입 버튼 클릭 시
  const handleSignup = async () => {
    // 모든 칸이 입력 되어야만 진행 가능
    if (
      !formData.id ||
      !formData.pw ||
      !formData.userName ||
      !formData.nickname
    ) {
      alert("모든 사용자 정보를 입력해주세요!!!");
      return;
    }

    // 백엔드 api 호출
    try {
      const response = await fetch("http://localhost:8080/api/auth/signup", {
        method: "POST",
        headers: {
          "Content-Type": "application/json", // JSON 형식으로 데이터 전송
        },
        body: JSON.stringify(formData),
      });

      // 서버에게 받은 메시지
      const result = await response.text();
      alert(result);

      // 가입 성공 시, 로그인 화면 이동
      if (result === "회원가입이 완료되었습니다. 환영합니다!") {
        onShowLogin();
      }
    } catch (error) {
      // 에러 발생 시...
      console.error("Error: ", error);
      alert("서버 오류가 발생했습니다. 잠시후 다시 시도하세요...");
    }
  };

  return (
    <div className="login-container">
      <div className="login-form-section">
        <div className="form-box">
          <h1 className="logo">PicStory</h1>
          <h2 className="signup-text">
            전세계의 수많은 사진과 동영상을 보고싶다면, 가입해주세요!
          </h2>
          <button className="facebook-login-btn">Facebook으로 로그인</button>

          <div className="divider">
            <div className="line"></div>
            <div className="text">또는</div>
            <div className="line"></div>
          </div>

          <form className="login-form">
            <input
              name="id"
              type="text"
              placeholder="휴대폰 또는 이메일 주소"
              onChange={handleChange}
            />
            <input
              name="pw"
              type="password"
              placeholder="비밀번호"
              onChange={handleChange}
            />
            <input
              name="userName"
              type="text"
              placeholder="성명"
              onChange={handleChange}
            />
            <input
              name="nickname"
              type="text"
              placeholder="사용자 이름"
              onChange={handleChange}
            />
            <button type="button" className="login-btn" onClick={handleSignup}>
              가입하기
            </button>
          </form>
        </div>

        <div className="signup-box">
          <p>
            이미 계정이 있으신가요?{" "}
            <span className="link" onClick={onShowLogin}>
              로그인
            </span>
          </p>
        </div>
      </div>
    </div>
  );
}

export default SignupPage;
