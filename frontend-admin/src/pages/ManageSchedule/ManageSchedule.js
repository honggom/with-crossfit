import styles from './ManageSchedule.module.css';
import React, { useEffect, useState } from 'react';
import { useNavigate, Link } from "react-router-dom";
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import koLocale from '@fullcalendar/core/locales/ko';
import { getSpecificSchedule } from '../../api/pages/ManageSchedule';
import { errorHandle } from '../../util/util';
import { getScheduleSetByBox } from '../../api/pages/SetDefaultSchedule/SetDefaultSchedule';

// TODO getSpecificSchedule 함수 완성 및 로직 처리

export default function ManageSchedule() {

    let navigate = useNavigate();

    const [events, setEvents] = useState([]);
    const [start, setStart] = useState(null);
    const [end, setEnd] = useState(null);


    // useEffect(() => {
    //     if (start !== null && end !== null) {
    //         // getSpecificSchedule(info.startStr, info.endStr).then((response) => {
    //         // }).catch((error) => {
    //         //     errorHandle(error, navigate);
    //         // });
    //             getScheduleSetByBox().then((response) => {
    //                 let current = new Date(start);
    //                 const endDay = new Date(end);
    //                 const tempEvents = [];
    //                 const scheduleSet = response.data;

    //                 while (true) {
    //                     if (current.getDate() === endDay.getDate() && current.getMonth() + 1=== endDay.getMonth() + 1) {
    //                         break;
    //                     } else {
    //                         const day = current.getDay();
    //                         let event;

    //                         const date = current.getDate() < 10 ?  '0' + current.getDate() : current.getDate();
    //                         const month = current.getMonth() + 1 < 10 ? '0' + current.getMonth() + 1 : current.getMonth() + 1;
    //                         const monthDate = `${current.getFullYear()}-${month}-${date}`;

    //                         const holiday = {title: '휴무', date: monthDate, color: 'red'}

    //                         if (day === 1) {
    //                             if (scheduleSet.monday !== null) {
    //                                 event = {title: scheduleSet.monday.name, date: monthDate};
    //                             } else {
    //                                 event = holiday;
    //                             }
    //                         } else if (day === 2) {
    //                             if (scheduleSet.tuesday !== null) {
    //                                 event = {title: scheduleSet.tuesday.name, date: monthDate};
    //                             } else {
    //                                 event = holiday;
    //                             }
    //                         } else if (day === 3) {
    //                             if (scheduleSet.wednesday !== null) {
    //                                 event = {title: scheduleSet.wednesday.name, date: monthDate};
    //                             } else {
    //                                 event = holiday;
    //                             }
    //                         } else if (day === 4) {
    //                             if (scheduleSet.thursday !== null) {
    //                                 event = {title: scheduleSet.thursday.name, date: monthDate};
    //                             } else {
    //                                 event = holiday;
    //                             }
    //                         } else if (day === 5) {
    //                             if (scheduleSet.friday !== null) {
    //                                 event = {title: scheduleSet.friday.name, date: monthDate};
    //                             } else {
    //                                 event = holiday;
    //                             }
    //                         } else if (day === 6) {
    //                             if (scheduleSet.saturady !== null) {
    //                                 event = {title: scheduleSet.saturady.name, date: monthDate};
    //                             } else {
    //                                 event = holiday;
    //                             }
    //                         } else if (day === 0) {
    //                             if (scheduleSet.sunday !== null) {
    //                                 event = {title: scheduleSet.sunday.name, date: monthDate};
    //                             } else {
    //                                 event = holiday;
    //                             }
    //                         }
    //                         tempEvents.push(event);
    //                         current = new Date(current.setDate(current.getDate() + 1));
    //                     }
    //                 }
    //                 setEvents(tempEvents);
    //             }).catch((error) => {
    //                 errorHandle(error, navigate);
    //             });
    //     }

    // }, [start, end]);

    return (
        <div className={styles.wrapper}>

            <div className={styles.titleWrapper}>
                <span>시간표 관리</span>
            </div>

            <div className={styles.middleWrapper}>
                <Link to='/set-default-schedule'>
                    <button className={styles.defaultScheduleButton}>기본 시간표 설정</button>
                </Link>
            </div>

            <div className={styles.calWrapper}>
                <FullCalendar

                    locales={[koLocale]}
                    plugins={[dayGridPlugin, interactionPlugin]}
                    initialView="dayGridMonth"
                    dateClick={(agrs) => {
                        console.log(agrs);
                    }}
                    eventDidMount={(events) => {
                        // getSpecificSchedule(info.startStr, info.endStr).then((response) => {
                        // }).catch((error) => {
                        //     errorHandle(error, navigate);
                        // });

                        getScheduleSetByBox().then((response) => {
                            let current = new Date(start);
                            const endDay = new Date(end);
                            const tempEvents = [];
                            const scheduleSet = response.data;

                            while (true) {
                                if (current.getDate() === endDay.getDate() && current.getMonth() + 1 === endDay.getMonth() + 1) {
                                    break;
                                } else {
                                    const day = current.getDay();
                                    let event;

                                    const date = current.getDate() < 10 ? '0' + current.getDate() : current.getDate();
                                    const month = current.getMonth() + 1 < 10 ? '0' + current.getMonth() + 1 : current.getMonth() + 1;
                                    const monthDate = `${current.getFullYear()}-${month}-${date}`;

                                    const holiday = { title: '휴무', date: monthDate, color: 'red' }

                                    if (day === 1) {
                                        if (scheduleSet.monday !== null) {
                                            event = { title: scheduleSet.monday.name, date: monthDate };
                                        } else {
                                            event = holiday;
                                        }
                                    } else if (day === 2) {
                                        if (scheduleSet.tuesday !== null) {
                                            event = { title: scheduleSet.tuesday.name, date: monthDate };
                                        } else {
                                            event = holiday;
                                        }
                                    } else if (day === 3) {
                                        if (scheduleSet.wednesday !== null) {
                                            event = { title: scheduleSet.wednesday.name, date: monthDate };
                                        } else {
                                            event = holiday;
                                        }
                                    } else if (day === 4) {
                                        if (scheduleSet.thursday !== null) {
                                            event = { title: scheduleSet.thursday.name, date: monthDate };
                                        } else {
                                            event = holiday;
                                        }
                                    } else if (day === 5) {
                                        if (scheduleSet.friday !== null) {
                                            event = { title: scheduleSet.friday.name, date: monthDate };
                                        } else {
                                            event = holiday;
                                        }
                                    } else if (day === 6) {
                                        if (scheduleSet.saturady !== null) {
                                            event = { title: scheduleSet.saturady.name, date: monthDate };
                                        } else {
                                            event = holiday;
                                        }
                                    } else if (day === 0) {
                                        if (scheduleSet.sunday !== null) {
                                            event = { title: scheduleSet.sunday.name, date: monthDate };
                                        } else {
                                            event = holiday;
                                        }
                                    }
                                    tempEvents.push(event);
                                    current = new Date(current.setDate(current.getDate() + 1));
                                }
                            }
                            setEvents(tempEvents);
                        }).catch((error) => {
                            errorHandle(error, navigate);
                        });
                    }}
                    // events={events}
                    events={[{ title: 'ㅁㄴ', date: '2021-11-24' }]}
                    datesSet={(info) => {

                        console.log(info);

                      //  if (start === null && end === null) {
                            setStart(new Date(info.start));
                            setEnd(new Date(info.end));
                       // }
                    }}
                    eventClick={(info) => {
                        console.log(info.event);
                        console.log(info.view);
                    }}
                />
            </div>
        </div>
    );
}