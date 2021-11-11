import styles from './ReservationTime.module.css';

export default function ReservationTime({ time, idx, currentReservationCount, maxReservationCount }) {

    return (
        <div className={styles.wrapper}>
            <p className={styles.time}>{time}</p>
            <p className={styles.reservationCountStatus}>{`(${currentReservationCount}/${maxReservationCount})`}</p>
        </div>
    );

};