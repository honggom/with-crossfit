import styles from './Logout.module.css';
import { useNavigate } from "react-router-dom";

export default function Logout() {

    let navigate = useNavigate();

    return (
        <>
            <button className={styles.editButton}>정보변경</button>
            <button className={styles.logoutButton}
                    onClick={() => {
                        navigate("/logout", { replace: true });
                    }}
            >로그아웃</button>
        </>
    );
}