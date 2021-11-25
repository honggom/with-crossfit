import styles from './SetDefaultSchedule.module.css';
import React, { useState, useEffect } from 'react';
import AddDefaultScheduleModal from '../../component/AddDefaultScheduleModal/AddDefaultScheduleModal';
import Schedule from './Schedule/Schedule';
import { getScheduleByBox, getScheduleSetByBox } from '../../api/pages/SetDefaultSchedule/SetDefaultSchedule';
import { errorHandle } from '../../util/util';
import { useNavigate } from "react-router-dom";
import ScheduleByDay from './ScheduleByDay/ScheduleByDay';


export default function SetDefaultSchedule() {

    let navigate = useNavigate();

    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [schedules, setSchedules] = useState([]);
    const [scheduleSet, setScheduleSet] = useState(null);

    useEffect(() => {

        getScheduleByBox().then((response) => {
            setSchedules(response.data);
        }).catch((error) => {
            errorHandle(error, navigate);
        });

        getScheduleSetByBox().then((response) => {
            setScheduleSet(response.data);
        }).catch((error) => {
            errorHandle(error, navigate);
        });

    }, []);

    return (
        <div className={styles.wrapper}>

            <div className={styles.titleWrapper1}>
                <span>기본 시간표 설정</span>
            </div>

            <div className={styles.titleWrapper2}>
                <span>저장된 기본 시간표</span>
            </div>

            <div className={styles.defaultScheduleWrapper}>
                <table className={styles.table}>
                    <thead>
                        <tr>
                            <th className={styles.th1}>시간표 명</th>
                            <th className={styles.th2}>
                                <button className={styles.addButton}
                                    onClick={() => {
                                        setModalIsOpen(true);
                                    }}
                                >
                                    +
                                </button>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <Schedule schedules={schedules} setSchedules={setSchedules} />
                    </tbody>
                </table>
            </div>

            <div className={styles.titleWrapper3}>
                <span>요일별 시간표</span>
            </div>

            <div className={styles.weekWrapper}>
                <table className={styles.table}>
                    <thead>
                        <tr>
                            <th className={styles.th3}>요일</th>
                            <th className={styles.th4}>설정된 시간표</th>
                            <th className={styles.th5}>변경</th>
                        </tr>
                    </thead>
                    <ScheduleByDay scheduleSet={scheduleSet} schedules={schedules} setScheduleSet={setScheduleSet} />
                </table>
            </div>
            <AddDefaultScheduleModal modalIsOpen={modalIsOpen} setModalIsOpen={setModalIsOpen} setSchedules={setSchedules} />
        </div>
    );
}