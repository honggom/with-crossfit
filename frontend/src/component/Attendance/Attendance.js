import React, { useState } from 'react';
import styles from './Attendance.module.css';
import ProgressBar from './ProgressBar/ProgressBar';

export default function Attendance() {

    const [startDay, setStartDay] = useState('');
    const [totalDay, setTotalDay] = useState(0);
    const [totalAttendanceDay, setTotalAttendanceDay] = useState(0);

    return (
        <div className={styles.wrapper}>

            <div className={styles.attendanceTitleWrapper}>
                <span className={styles.title}>출석률</span>
            </div>

            <div className={styles.progressBarWrapper}>
                <ProgressBar totalDay={totalDay} totalAttendanceDay={totalAttendanceDay} />
            </div>

            <div className={styles.startDayWrapper}>
                <span className={styles.startDayTitle}>시작일</span>
                <span className={styles.startDay}>: {startDay}</span>
            </div>

            <div className={styles.totalDayWrapper}>
                <span className={styles.totalDayTitle}>등록 일수</span>
                <span className={styles.totalDay}>: {totalDay}일</span>
            </div>

            <div className={styles.totalAttendanceDayWrapper}>
                <span className={styles.totalAttendanceDayTitle}>출석 일수</span>
                <span className={styles.totalAttendanceDay}>: {totalAttendanceDay}일</span>
            </div>

        </div>
    );
}