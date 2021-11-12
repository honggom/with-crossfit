import styles from './Nav.module.css';

// WOD작성, 레코드작성, 예약확인, 예약(휴무/스케줄)관리, 공지작성, 회원관리(등록일수관리, 승급포인트, 돌직구, 지점지정, 휴회관리), 신청곡?


// 칠드런메뉴 컴포넌트 만들고 칠드런메뉴 프롭으로 온마우스 true / false를 전달하면 될 듯 ??

export default function Nav() {
    return (
        <div className={styles.wrapper}>
            <div className={styles.menuWrapper}>
                <div className={styles.menu} onMouseOver={() => {
                    console.log("hi")
                }}>
                    <span>1</span>
                    <span className={styles.childrenMenu}>2</span>
                    <span className={styles.childrenMenu}>3</span>
                </div>
            </div>
            <div className={styles.menuWrapper}>
                <div className={styles.menu}>
                    <span>1</span>
                    <span className={styles.childrenMenu}>2</span>
                </div>
            </div>
        </div>
    );
}