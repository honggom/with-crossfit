import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Login, Home } from './pages';
import styles from './App.module.css';
import './index.css';
import Nav from './component/Nav/Nav';

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
          </Routes>
        </section>
      </BrowserRouter>
    </div>
  );
}