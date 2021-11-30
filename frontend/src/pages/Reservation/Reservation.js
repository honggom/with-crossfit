import styles from './Reservation.module.css';
import ReservationTime from './ReservationTime/ReservationTime';
import React, { useState, useEffect } from 'react';
import { errorHandle, sortByTime, changeToString } from '../../util/util';
import { getReservation, getReservationStatus } from '../../api/pages/Reservation';
import { useNavigate } from "react-router-dom";

export default function Reservation() {

    const [reservationStatus, setReservationStatus] = useState(null);
    const [ams, setAms] = useState([]);
    const [pms, setPms] = useState([]);
    const [date, setDate] = useState('');

    let navigate = useNavigate();

    useEffect(() => {
        const dt = new Date();
        setDate(`${dt.getFullYear()}.${dt.getMonth() + 1}.${dt.getDate()}(${changeToString(dt.getDay())})`);

        getReservationStatus().then((res1) => {
            setReservationStatus(res1.data);
            getReservation().then((res2) => {
                const times = sortByTime(res2.data);

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

            }).catch((error) => {
                errorHandle(error, navigate);
            });
        }).catch((error) => {
            errorHandle(error, navigate);
        });
    }, []);

    return (
        <div className={styles.wrapper}>

            <div className={styles.topWrapper}>
                <span className={styles.day}>{date}</span>
                <span>수업 예약</span>
            </div>

            <div className={styles.reservationStatusWrapper}>
                <span>내 예약 상태 :</span>
                <span className={styles.reservationStatus}> 
                    {reservationStatus && reservationStatus.reserved ? 
                    `예약 완료 (${reservationStatus.start.substring(0, 5)} ~ ${reservationStatus.end.substring(0, 5)})` : 
                    '예약 가능'}
                </span>
            </div>

            <div className={styles.amTitleWrapper}>
                <span className={styles.amTitle}>오전</span>
            </div>

            <div className={styles.reservationTimeWrapper}>
                {ams && ams.map(am => (
                    <ReservationTime key={am.id} idx={am.id} start={am.start} time={am.start + ' ~ ' + am.end}
                        currentReservationCount={am.reservationCount}
                        maxReservationCount={am.maxReservation}
                        reservationStatus={reservationStatus}
                    />
                ))}
            </div>

            <p className={styles.dotLine}></p>

            <div className={styles.pmTitleWrapper}>
                <span className={styles.pmTitle}>오후</span>
            </div>

            <div className={styles.reservationTimeWrapper}>
                {pms && pms.map(pm => (
                    <ReservationTime key={pm.id} idx={pm.id} start={pm.start} time={pm.start + ' ~ ' + pm.end}
                        currentReservationCount={pm.reservationCount}
                        maxReservationCount={pm.maxReservation}
                        reservationStatus={reservationStatus}
                    />
                ))}
            </div>

        </div>
    );
}