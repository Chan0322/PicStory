import { useState } from "react";

import LoginPage from "./pages/LoginPage";
import SignupPage from "./pages/SignupPage";

function App() {
  const [view, setView] = useState("login");

  // 로그인 성공 시
  const handleLoginSuccess = () => {
    setView("feed");
  };

  return (
    <div className="App">
      {/* 메인 피드(로그인 성공 시) */}
      {view === "feed" && (
        <div style={{ textAlign: "center" }}>
          <nav style={{ borderBottom: "1px solid gray", padding: "10px" }}>
            <h1>PicStory Feed</h1>
          </nav>
          <h2>PicStory 메인 피드 개발 중입니다... 빠른 시일 내에 만나요!😀</h2>
          <button onClick={() => setView("login")}>로그아웃</button>
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
