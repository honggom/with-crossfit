import Modal from 'react-modal';
import styles from './ReadScheduleModal.module.css';
import React, { useState } from 'react';
import { useEffect } from 'react';
import { deleteScheduleById } from '../../api/component/ReadScheduleModal/ReadScheduleModal';
import { useNavigate } from "react-router-dom";
import { errorHandle } from '../../util/util';
import { getScheduleByBox } from '../../api/pages/SetDefaultSchedule/SetDefaultSchedule';

export default function ReadScheduleModal({ isOpen, setIsOpen, name, eachTimes, scheduleId, setSchedules }) {

    let navigate = useNavigate();

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

    function sortByTime(arr) {
        const temp = [...arr];

        temp.sort((a, b) => {
            const aTimes = a.start.split(':');
            const bTimes = b.start.split(':');
            const aTime = Number(aTimes[0] + aTimes[1]);
            const bTime = Number(bTimes[0] + bTimes[1]);

            if (aTime > bTime) {
                return 1;
            }

            if (aTime < bTime) {
                return -1;
            }
            return 0;
        })
        return temp;
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
            <Modal isOpen={isOpen} style={{ content: content }}>

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
                                deleteScheduleById(scheduleId).then((response) => {
                                    alert('삭제되었습니다.');
                                    setIsOpen(false);
                                    getScheduleByBox().then((response) => {
                                        setSchedules(response.data);
                                    }).catch((error) => {
                                        errorHandle(error, navigate);
                                    });
                                }).catch((error) => {
                                    if (error.response.status === 400) {
                                        alert(error.response.data);
                                    } else {
                                        errorHandle(error, navigate)
                                    }
                                });
                            }
                        }}
                    >
                        삭제
                    </button>
                    <button className={styles.closeButton}
                        onClick={() => { setIsOpen(false); }}
                    >
                        X
                    </button>
                </div>
            </Modal>
        </>
    );
}