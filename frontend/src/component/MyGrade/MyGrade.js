import styles from './MyGrade.module.css';

export default function MyGrade() {

    return (
        <div className={styles.myGrade}>
            <div className={styles.gradeWrapper}>
                <span>등급 : 레인보우</span>
            </div>
        </div>
    );
}