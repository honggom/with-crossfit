import styles from './Reservation.module.css';
import ReservationTime from './ReservationTime/ReservationTime';
import React, { useState, useEffect } from 'react';
import { sortByTime } from '../../util/util';
import { getReservation } from '../../api/pages/Reservation';

export default function Reservation() {

    const [reservationStatus, setReservationStatus] = useState("예약 완료");
    const time = '11:00';
    const currentReservationCount = 7;
    const maxReservationCount = 16;

    const [ams, setAms] = useState([]);
    const [pms, setPms] = useState([]);

    // TODO 여기 완성 ㄱ 

    useEffect(() => {
        getReservation().then((response) => {
            console.log(response.data);
        }).catch((error) => {

        });
        /*
        const times = sortByTime();

        const tempAms = [];
        const tempPms = [];

        for (const time of times) {
            const hour = Number(time.start.split(':')[0]);
            const splitedStart = time.start.split(':');
            const splitedEnd = time.end.split(':');

            time.start = splitedStart[0] + ':' + splitedStart[1];
            time.end = splitedEnd[0] + ':' + splitedEnd[1];

            if (hour < 12) {
                tempAms.push(time);
            } else {
                tempPms.push(time);
            }
        }
        setAms(tempAms);
        setPms(tempPms);
        */ 
    }, []);

    return (
        <div className={styles.wrapper}>

            <div className={styles.topWrapper}>
                <span className={styles.day}>2020.11.11(금)</span>
                <span>수업 예약</span>
            </div>

            <div className={styles.reservationStatusWrapper}>
                <span>내 예약 상태 :</span>
                <span className={styles.reservationStatus}> {reservationStatus}</span>
            </div>

            <div className={styles.amTitleWrapper}>
                <span className={styles.amTitle}>오전</span>
            </div>

            <div className={styles.reservationTimeWrapper}>
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />

            </div>

            <p className={styles.dotLine}></p>

            <div className={styles.pmTitleWrapper}>
                <span className={styles.pmTitle}>오후</span>
            </div>

            <div className={styles.reservationTimeWrapper}>
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
                <ReservationTime time={time}
                    currentReservationCount={currentReservationCount}
                    maxReservationCount={maxReservationCount}
                />
            </div>

        </div>
    );
}