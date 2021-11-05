import styles from './MyName.module.css';

export default function MyName() {

    return (
        <div className={styles.myName}>
            <div className={styles.nameWrapper}>
                <span>이름 : 변재홍</span>
            </div>
        </div>
    );
}