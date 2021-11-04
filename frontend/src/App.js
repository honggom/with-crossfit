import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Home, Login, Logout } from './pages';
import Nav from './component/Nav/Nav';
import styles from './App.module.css';
import { isMobile, isBrowser } from 'react-device-detect';

export default function App() {

    console.log(isBrowser);
    console.log(isMobile);

    return (
        <div className={styles.app}>
            <section>
                <BrowserRouter>
                    <Routes>
                        <Route path="/" element={<Login />} exact />
                        <Route path="/logout" element={<Logout />} exact />
                        <Route path="/home" element={<Home />} exact />
                    </Routes>
                </BrowserRouter>
            </section>
            <nav className={styles.nav}>
                <Nav />
            </nav>
        </div>
    );
}