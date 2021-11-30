import styles from './ReservationTime.module.css';
import { insertReservationTimeRelation, deleteReservationTimeRelation } from '../../../api/pages/ReservationTime';
import { useEffect, useState } from 'react';
import { errorHandle } from '../../../util/util';
import { useNavigate } from "react-router-dom";

export default function ReservationTime({ reservationStatus, start, time, idx, currentReservationCount, maxReservationCount }) {

    let navigate = useNavigate();

    const [givenTime, setGivenTime] = useState(new Date());
    const [now, setNow] = useState(new Date());

    useEffect(() => {
        const times = start.split(':');

        const eachTime = new Date();
        eachTime.setHours(Number(times[0]));
        eachTime.setMinutes(Number(times[1]));
        eachTime.setSeconds(0);

        setGivenTime(eachTime);
    }, []);


    // 예약 가능 시간이 지난 경우
    if (now >= givenTime) {
        return (
            <div key={idx}
                className={styles.wrapper3}
                onClick={() => {
                    alert('예약 가능 시간이 아닙니다.');
                }}
            >
                <p className={styles.time}>{time}</p>
                <p className={styles.reservationCountStatus}>{`(${currentReservationCount}/${maxReservationCount})`}</p>
            </div>
        );
    
    // 예약 가능 시간인 경우
    } else {

        // 예약 내역이 없는 경우
        if (reservationStatus.reservationTimeId === null) {

            // 예약 인원이 가득 찬 경우
            if (currentReservationCount >= maxReservationCount) {
                return (
                    <div key={idx}
                        className={styles.wrapper3}
                    >
                        <p className={styles.time}>{time}</p>
                        <p className={styles.reservationCountStatus}>{`(${currentReservationCount}/${maxReservationCount})`}</p>
                    </div>
                );
            
            // 예약 인원이 가득 차지 않은 경우
            } else {
                return (
                    <div key={idx}
                        className={styles.wrapper}
                        onClick={() => {
                            if (window.confirm(`${time} 수업을 예약하시겠습니까?`)) {
                                insertReservationTimeRelation(idx).then((response) => {
                                    alert('예약되었습니다.');
                                    window.location.reload();
                                }).catch((error) => {
                                    if (error.response.status === 400) {
                                        alert(error.response.data);
                                    } else {
                                        errorHandle(error, navigate);
                                    }
                                });
                            }
                        }}
                    >
                        <p className={styles.time}>{time}</p>
                        <p className={styles.reservationCountStatus}>{`(${currentReservationCount}/${maxReservationCount})`}</p>
                    </div>
                );
            }

        // 예약 내역이 있는 경우
        } else {

            // 예약한 시간인 경우
            if (reservationStatus.reservationTimeId === idx) {
                return (
                    <div key={idx}
                        className={styles.wrapper2}
                        onClick={() => {
                            if (window.confirm('에약을 취소하시겠습니까?')) {
                                deleteReservationTimeRelation(idx).then((response) => {
                                    alert('취소되었습니다.');
                                    window.location.reload();
                                }).catch((error) => {
                                    errorHandle(error, navigate);
                                })
                            }
                        }}
                    >
                        <p className={styles.time}>{time}</p>
                        <p className={styles.reservationCountStatus}>{`(${currentReservationCount}/${maxReservationCount})`}</p>
                    </div>
                );

            // 예약한 시간이 아닌 경우
            } else {
                return (
                    <div key={idx}
                        className={styles.wrapper3}
                        onClick={() => {
                            alert('이미 다른 시간을 예약하셨습니다.');
                        }}
                    >
                        <p className={styles.time}>{time}</p>
                        <p className={styles.reservationCountStatus}>{`(${currentReservationCount}/${maxReservationCount})`}</p>
                    </div>
                );
            }
        }
    }
};