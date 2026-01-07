import { useState } from "react";

import LoginPage from "./pages/LoginPage";

function App() {
  // isLoggedIn = false -> 로그인 페이지, true -> 메인 페이지
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // 로그인 버튼 클릭
  const handleLogin = () => {
    setIsLoggedIn(true);
  };

  return (
    <div className="App">
      {isLoggedIn ? (
        // 로그인 성공
        <div style={{ textAlign: "center" }}>
          <nav style={{ borderBottom: "1px solid #dbdbdb", padding: "10px" }}>
            <h1>PicStory Feed</h1>
          </nav>
          <h2>PicStory 메인 피드 개발중입니다.</h2>
          <button onClick={() => setIsLoggedIn(false)}>로그아웃</button>
        </div>
      ) : (
        // 로그인 전
        <LoginPage onLogin={handleLogin} />
      )}
    </div>
  );
}

export default App;

// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
// import './App.css'

// function App() {
//   const [count, setCount] = useState(0)

//   return (
//     <>
//       <div>
//         <a href="https://vite.dev" target="_blank">
//           <img src={viteLogo} className="logo" alt="Vite logo" />
//         </a>
//         <a href="https://react.dev" target="_blank">
//           <img src={reactLogo} className="logo react" alt="React logo" />
//         </a>
//       </div>
//       <h1>Vite + React</h1>
//       <div className="card">
//         <button onClick={() => setCount((count) => count + 1)}>
//           count is {count}
//         </button>
//         <p>
//           Edit <code>src/App.jsx</code> and save to test HMR
//         </p>
//       </div>
//       <p className="read-the-docs">
//         Click on the Vite and React logos to learn more
//       </p>
//     </>
//   )
// }

// export default App
