import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Login, Home, UserRegister, WriteWod, WodHistory, Logout, ReadWod } from './pages';
import styles from './App.module.css';
import './index.css';
import Nav from './component/Nav/Nav';

// TODO 와드 수정 기능 

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
            <Route path="/logout" element={<Logout />} exact />
            <Route path="/home" element={<Home />} exact />
            <Route path="/read-wod/:wodId" element={<ReadWod />} exact />
            <Route path="/write-wod" element={<WriteWod />} exact />
            <Route path="/wod-history" element={<WodHistory />} exact />
            <Route path="/user-register" element={<UserRegister />} exact />
          </Routes>
        </section>
      </BrowserRouter>
    </div>
  );
}