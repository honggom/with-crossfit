import styles from './SetDefaultSchedule.module.css';
import React, { useState, useEffect } from 'react';
import AddDefaultScheduleModal from '../../component/AddDefaultScheduleModal/AddDefaultScheduleModal';
import Schedule from './Schedule/Schedule';
import { getScheduleByBox } from '../../api/pages/SetDefaultSchedule/SetDefaultSchedule';
import { errorHandle } from '../../util/util';
import { useNavigate } from "react-router-dom";


// TODO 요일별 시간표 기능 만들기


export default function SetDefaultSchedule() {

    let navigate = useNavigate();

    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [schedules, setSchedules] = useState([]);

    useEffect(() => {
        getScheduleByBox().then((response) => {
            setSchedules(response.data);
        }).catch((error) => {
            errorHandle(error, navigate);
        });
    },[]);

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
                            <th className={styles.th5}>수정</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td className={styles.td2}><span>월요일</span></td>
                            <td className={styles.td3}><span>평일</span></td>
                            <td className={styles.td4}><span><button>edit</button></span></td>
                        </tr>

                        <tr>
                            <td className={styles.td2}><span>화요일</span></td>
                            <td className={styles.td3}><span>평일</span></td>
                            <td className={styles.td4}><span><button>edit</button></span></td>
                        </tr>

                        <tr>
                            <td className={styles.td2}><span>수요일</span></td>
                            <td className={styles.td3}><span>평일</span></td>
                            <td className={styles.td4}><span><button>edit</button></span></td>
                        </tr>

                        <tr>
                            <td className={styles.td2}><span>목요일</span></td>
                            <td className={styles.td3}><span>평일</span></td>
                            <td className={styles.td4}><span><button>edit</button></span></td>
                        </tr>

                        <tr>
                            <td className={styles.td2}><span>금요일</span></td>
                            <td className={styles.td3}><span>평일</span></td>
                            <td className={styles.td4}><span><button>edit</button></span></td>
                        </tr>

                        <tr>
                            <td className={styles.td2}><span>토요일</span></td>
                            <td className={styles.td3}><span>평일</span></td>
                            <td className={styles.td4}><span><button>edit</button></span></td>
                        </tr>

                        <tr>
                            <td className={styles.td2}><span>일요일</span></td>
                            <td className={styles.td3}><span>평일</span></td>
                            <td className={styles.td4}><span><button>edit</button></span></td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <AddDefaultScheduleModal modalIsOpen={modalIsOpen} setModalIsOpen={setModalIsOpen} setSchedules={setSchedules} />

        </div>
    );
}