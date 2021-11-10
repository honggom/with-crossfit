import styles from './Nav.module.css';
import { Link } from 'react-router-dom';
import { FaRegCalendarCheck } from "react-icons/fa";
import { MdPersonOutline, MdOutlineArticle } from "react-icons/md";
import { GiWeightLiftingUp } from "react-icons/gi";

export default function Nav() {
    return (
        <>
            <div className={styles.wrapper}>
                <div className={`${styles.wod} ${styles.button}`}>
                    <Link to="/wod">
                        <div className={styles.wodIconWrapper}>
                            <GiWeightLiftingUp className={styles.wodIcon} />
                        </div>
                    </Link>
                </div>
                <div className={`${styles.reservation} ${styles.button}`}>
                    <Link to="/reservation">
                        <div className={styles.reservationIconWrapper}>
                            <FaRegCalendarCheck className={styles.reservationIcon} />
                        </div>
                    </Link>
                </div>
                <div className={`${styles.board} ${styles.button}`}>
                    <Link to="/board">
                        <div className={styles.boardIconWrapper}>
                            <MdOutlineArticle className={styles.boardIcon} />
                        </div>
                    </Link>
                </div>
                <div className={`${styles.mypage} ${styles.button}`}>
                    <Link to="/mypage">
                        <div className={styles.mypageIconWrapper}>
                            <MdPersonOutline className={styles.mypageIcon} />
                        </div>
                    </Link>
                </div>
            </div>
        </>
    );
}