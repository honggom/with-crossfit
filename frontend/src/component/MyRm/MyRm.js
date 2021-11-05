import styles from './MyRm.module.css';

export default function MyRm() {

    return (
        <div className={styles.myRm}>
            <div className={styles.myRmTitleWrapper}>
                <span>내 기록</span>
            </div>
            <div className={styles.myRmListWrapper}>
                <ul>
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>1</li>
                </ul>
            </div>
        </div>
    );
}