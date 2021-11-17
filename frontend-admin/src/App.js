import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Login, Home, UserRegister, WriteWod, WodHistory } from './pages';
import styles from './App.module.css';
import './index.css';
import Nav from './component/Nav/Nav';

// TODO 로그인 확인 처리

export default function App() {
  return (
    <div className={styles.app}>
      <BrowserRouter>
        <nav>
          <Nav />
        </nav>
        <section>
          <Routes>
            <Route path="/" element={<Login />} exact />
            <Route path="/home" element={<Home />} exact />
            <Route path="/write-wod" element={<WriteWod />} exact />
            <Route path="/wod-history" element={<WodHistory />} exact />
            <Route path="/user-register" element={<UserRegister />} exact />
          </Routes>
        </section>
      </BrowserRouter>
    </div>
  );
}