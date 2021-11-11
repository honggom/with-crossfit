import styles from './MyPage.module.css';
import ProfileImage from '../../component/ProfileImage/ProfileImage';
import MyName from '../../component/MyName/MyName';
import MyRm from '../../component/MyRm/MyRm';
import MyGrade from '../../component/MyGrade/MyGrade';
import Attendance from '../../component/Attendance/Attendance';
import Logout from '../../component/Logout/Logout';

export default function MyPage() {

    return (
        <div className={styles.mypage}>
            <div className={styles.topWrapper}>
                <div className={styles.profileImageWrapper}>
                    <ProfileImage />
                </div>
                <div className={styles.profileWrapper}>
                    <div className={styles.nameWrapper}>
                        <MyName />
                    </div>
                    <div className={styles.gradeWrapper}>
                        <MyGrade />
                    </div>
                    <div className={styles.logoutWrapper}>
                        <Logout />
                    </div>
                </div>
            </div>

            <div className={styles.middleWrapper}>
                <MyRm />
            </div>

            <div className={styles.bottomWrapper}>
                <Attendance />
            </div>
        </div>
    );
}