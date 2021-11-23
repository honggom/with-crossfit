import styles from './Nav.module.css';
import Menu from '../Menu/Menu';
import { Link } from 'react-router-dom';

// WOD작성, 레코드작성, 예약확인, 예약(휴무/스케줄)관리, 공지작성, 
// 회원관리(등록일수관리, 승급포인트, 돌직구, 지점지정, 휴회관리)

const wodChildren = [
    {
        title: 'WOD 작성',
        link: '/write-wod'
    },
    {
        title: 'WOD 이력',
        link: '/wod-history'
    }
]

const userRegisterChildren = [
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

            <div className={styles.menuWrapper}>
                <Menu title={'WOD'} childrens={wodChildren} />
            </div>

            <Link to="/manage-schedule">
                <div className={styles.menuWrapper}>
                    <Menu title={'시간표 관리'} />
                </div>
            </Link>

            <div className={styles.menuWrapper}>
                <Menu title={'회원 관리'} childrens={userRegisterChildren} />
            </div>

        </div>
    );
}