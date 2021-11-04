import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Home, Login, Logout } from './pages';

export default function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login />} exact />
          <Route path="/logout" element={<Logout />} exact />
          <Route path="/home" element={<Home />} exact />
        </Routes>
      </BrowserRouter>
    </div>
  );
}