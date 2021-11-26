import Modal from 'react-modal';
import styles from './ReadSpecificScheduleModal.module.css';
import React, { useState } from 'react';
import { useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import { errorHandle, sortByTime } from '../../util/util';
import { deleteSpecificScheduleById } from '../../api/component/ReadSpecificScheduleModal/ReadSpecificScheduleModal';

export default function ReadSpecificScheduleModal({ isSpecificOpen, setIsSpecificOpen, name, eachTimes, id }) {

    let navigate = useNavigate();
    
    const overlay = {
        zIndex: '1'
    }

    const content = {
        display: 'flex',
        flexDirection: 'column',
        position: 'absolute',
        top: '15%',
        height: '60%',
        margin: 'var(--comm-margin)',
        borderRadius: 'var(--comm-border-radius)',
        overflowX: 'hidden'
    }

    const [ams, setAms] = useState([]);
    const [pms, setPms] = useState([]);

    useEffect(() => {
        const times = sortByTime(eachTimes);

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
    }, [eachTimes]);

    return (
        <>
            <Modal isOpen={isSpecificOpen} style={{ overlay: overlay, content: content }}>

                <div className={styles.rowWrapper1}>
                    <span className={styles.font1}>{name}</span>
                </div>

                <div className={styles.rowWrapper1left}>
                    <span className={styles.font2}>오전</span>
                </div>

                <div className={styles.timesWrapper}>
                    {ams.map(am => (
                        <div className={styles.eachTime} key={am.id}>
                            <span>{`${am.start} ~ ${am.end}`}</span>
                        </div>
                    ))}
                </div>

                <div className={styles.rowWrapper1left}>
                    <span className={styles.font2}>오후</span>
                </div>

                <div className={styles.timesWrapper}>
                    {pms.map(pm => (
                        <div className={styles.eachTime} key={pm.id}>
                            <span>{`${pm.start} ~ ${pm.end}`}</span>
                        </div>
                    ))}
                </div>

                <div className={styles.rowWrapper2}>
                    <button className={styles.deleteButton}
                        onClick={() => {
                            if (window.confirm("삭제하시겠습니까?")) {
                                deleteSpecificScheduleById(id).then((response) => {
                                    alert("삭제되었습니다.");
                                    window.location.reload();
                                }).catch((error) => {
                                    errorHandle(error, navigate);
                                });
                            }
                        }}
                    >
                        삭제
                    </button>
                    <button className={styles.closeButton}
                        onClick={() => { setIsSpecificOpen(false); }}
                    >
                        X
                    </button>
                </div>
            </Modal>
        </>
    );
}