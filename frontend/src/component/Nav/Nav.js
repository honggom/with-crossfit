import styles from './Nav.module.css';
import { Link } from 'react-router-dom';

export default function Nav() {
    return (
        <>
            <div className={styles.wrapper}>
                <div className={`${styles.wod} ${styles.button}`}>
                    <Link to="/wod">
                        wod
                    </Link>
                </div>
                <div className={`${styles.reservation} ${styles.button}`}>
                    <Link to="/reservation">
                        reservation
                    </Link>
                </div>
                <div className={`${styles.board} ${styles.button}`}>
                    <Link to="/board">
                        board
                    </Link>
                </div>
                <div className={`${styles.mypage} ${styles.button}`}>
                    <Link to="/mypage">
                        mypage
                    </Link>
                </div>
            </div>
        </>
    );
}