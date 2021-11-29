import styles from './MyPage.module.css';
import ProfileImage from '../../component/ProfileImage/ProfileImage';
import MyName from '../../component/MyName/MyName';
import MyRm from '../../component/MyRm/MyRm';
import MyGrade from '../../component/MyGrade/MyGrade';
import Attendance from '../../component/Attendance/Attendance';
import Logout from '../../component/Logout/Logout';
import { getUser } from '../../api/component/MyPage';
import { useEffect, useState } from 'react';
import { useNavigate } from "react-router-dom";
import { errorHandle } from '../../util/util';

export default function MyPage() {

    // TODO 출석률 부분 기능 추가

    let navigate = useNavigate();

    const [grade, setGrade] = useState('');
    const [name, setName] = useState('');

    useEffect(() => {
        getUser().then((response) => {
            const user = response.data;
            setGrade(user.grade);
            setName(user.name);

        }).catch((error) => {
            errorHandle(error, navigate);
        });
    }, []);

    return (
        <div className={styles.mypage}>
            <div className={styles.topWrapper}>
                <div className={styles.profileImageWrapper}>
                    <ProfileImage />
                </div>
                <div className={styles.profileWrapper}>
                    <div className={styles.nameWrapper}>
                        <MyName name={name}/>
                    </div>
                    <div className={styles.gradeWrapper}>
                        <MyGrade grade={grade}/>
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