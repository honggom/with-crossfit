import styles from './MyPage.module.css';
import ProfileImage from '../../component/ProfileImage/ProfileImage';
import MyName from '../../component/MyName/MyName';
import MyRm from '../../component/MyRm/MyRm';
import MyGrade from '../../component/MyGrade/MyGrade';
import Attendance from '../../component/Attendance/Attendance';

export default function MyPage() {
    /**
     * 1. 정보 변경
     * 2. RM 체크, %변환 KG변환
     * 3. 출석률
     * 4. 프로필 이미지, 이름, 등급
     */
    return (
        <div className={styles.mypage}>

            <div className={styles.topWrapper}>
                <div className={styles.profileImageWrapper}>
                    <ProfileImage />
                </div>
                <div className={styles.nameAndGradeWrapper}>
                    <MyName />
                    <MyGrade />
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