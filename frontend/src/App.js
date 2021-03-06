import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Home, Login, Logout, Wod, Reservation, Board, MyPage, NotRegistered } from './pages';
import Nav from './component/Nav/Nav';
import styles from './App.module.css';
import './index.css';
// import { isMobile, isBrowser } from 'react-device-detect';

export default function App() {

    // console.log(isBrowser);
    // console.log(isMobile);

    return (
        <div className={styles.app}>
            <BrowserRouter>
                <section>
                    <Routes>
                        <Route path="/" element={<Login />} exact />
                        <Route path="/logout" element={<Logout />} exact />
                        <Route path="/home" element={<Home />} exact />
                        <Route path="/wod" element={<Wod />} exact />
                        <Route path="/reservation" element={<Reservation />} exact />
                        <Route path="/board" element={<Board />} exact />
                        <Route path="/mypage" element={<MyPage />} exact />
                        <Route path="/not-registered" element={<NotRegistered />} exact />
                    </Routes>
                </section>
                <nav>
                    <Nav />
                </nav>
            </BrowserRouter>
        </div>
    );
}