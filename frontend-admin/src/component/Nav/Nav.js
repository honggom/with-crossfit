import styles from './Nav.module.css';
import Menu from '../Menu/Menu';
import { Link } from 'react-router-dom';

// WOD작성, 레코드작성, 예약확인, 예약(휴무/스케줄)관리, 공지작성, 
// 회원관리(등록일수관리, 승급포인트, 돌직구, 지점지정, 휴회관리)

const children = [
    {
        title: '회원 등록',
        link: '/user-register'
    },
    {
        title: '휴회 관리',
        link: ''
    }
]

export default function Nav() {
    return (
        <div className={styles.wrapper}>

            <div className={styles.topWrapper}>
                <span>With Crossfit</span>
            </div>

            <Link to={'/write-wod'}>
                <div className={styles.menuWrapper}>
                    <Menu title={'WOD 작성'} />
                </div>
            </Link>

            <div className={styles.menuWrapper}>
                <Menu title={'예약 확인'} />
            </div>

            <div className={styles.menuWrapper}>
                <Menu title={'회원 관리'} childrens={children} />
            </div>

        </div>
    );
}