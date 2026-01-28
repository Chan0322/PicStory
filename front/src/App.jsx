import { useState, useEffect } from "react";

import LoginPage from "./pages/LoginPage";
import SignupPage from "./pages/SignupPage";

function App() {
  // 초기 접속 시 로컬스토리지 확인.
  // 액세스 토큰 존재 시, 메인 피드로
  const [view, setView] = useState(() => {
    return localStorage.getItem("accessToken") ? "feed" : "login";
  });

  // 로그아웃
  const handleLogout = async () => {
    // 서버에 로그아웃 요청
    await fetch("http://localhost:8080/api/auth/logout", {
      method: "POST",
      credentials: "include",
    });

    localStorage.removeItem("accessToken");
    localStorage.removeItem("nickname");
    setView("login");
  };

  // 로그인 성공 시
  const handleLoginSuccess = () => {
    setView("feed");
  };

  // 토큰 유효성 검사
  useEffect(() => {
    const checkLogin = async () => {
      let token = localStorage.getItem("accessToken");

      // 토큰이 없다면 재발급
      if (!token) {
        try {
          const response = await fetch(
            "http://localhost:8080/api/auth/refresh",
            {
              method: "POST",
              credentials: "include",
            },
          );

          if (response.ok) {
            const data = await response.json();
            token = data.accessToken;
            localStorage.setItem("accessToken", token);
            console.log("토큰 재발급");
          } else {
            throw new Error("refresh 토큰 만료/없음");
          }
        } catch (err) {
          handleLogout();
          return;
        }
      }

      // 토큰이 있다면 유효성 검사
      fetch("http://localhost:8080/api/user/me", {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        credentials: "include",
      })
        .then((res) => {
          if (!res.ok) throw new Error("만료된 토큰");
          return res.json();
        })
        .then((data) => {
          // 성공 시 닉네임 정보 갱신
          localStorage.setItem("nickname", data.nickname);
          setView("feed");
        })
        .catch((err) => {
          // 토큰이 변조되었거나 만료인 경우 로그아웃
          console.error("인증 실패: ", err);
          handleLogout();
        });
    };
    checkLogin();
  }, []);

  return (
    <div className="App">
      {/* 메인 피드(로그인 성공 시) */}
      {view === "feed" && (
        <div style={{ textAlign: "center" }}>
          <nav style={{ borderBottom: "1px solid gray", padding: "10px" }}>
            <h1>PicStory Feed</h1>
          </nav>
          <h2>PicStory 메인 피드 개발 중입니다... 빠른 시일 내에 만나요!😀</h2>
          <p>{localStorage.getItem("nickname")}님 어서오세요</p>
          <button onClick={handleLogout}>로그아웃</button>
        </div>
      )}

      {/* 로그인 페이지 */}
      {view === "login" && (
        <LoginPage
          onLogin={handleLoginSuccess}
          onShowSignup={() => setView("signup")}
        />
      )}

      {/* 회원가입 페이지 */}
      {view === "signup" && <SignupPage onShowLogin={() => setView("login")} />}
    </div>
  );
}

export default App;
