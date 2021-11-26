import styles from './ManageSchedule.module.css';
import React, { useState } from 'react';
import { useNavigate, Link } from "react-router-dom";
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import koLocale from '@fullcalendar/core/locales/ko';
import { getSpecificSchedule, getSpecificScheduleByDate } from '../../api/pages/ManageSchedule';
import { errorHandle } from '../../util/util';
import { getScheduleSetByBox } from '../../api/pages/SetDefaultSchedule/SetDefaultSchedule';
import WriteSpecificScheduleModal from '../../component/WriteSpecificScheduleModal/WriteSpecificScheduleModal';
import ReadSpecificScheduleModal from '../../component/ReadSpecificScheduleModal/ReadSpecificScheduleModal';
import { deleteSpecificScheduleById } from '../../api/component/ReadSpecificScheduleModal/ReadSpecificScheduleModal';

export default function ManageSchedule() {

    // TODO 이전 날 ~ 오늘 까지는 스케쥴 작성 불가능

    let navigate = useNavigate();

    const [events, setEvents] = useState([]);
    const [start, setStart] = useState('');
    const [end, setEnd] = useState('');
    const [isOpen, setIsOpen] = useState(false);
    const [dateStr, setDateStr] = useState('');

    const [isSpecificOpen, setIsSpecificOpen] = useState(false);
    const [eachTimes, setEachTimes] = useState([]);
    const [name, setName] = useState('');
    const [specificId, setSpecificId] = useState(null);

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
                    dateClick={(info) => {
                        getSpecificScheduleByDate(info.dateStr).then((response) => {
                            const specific = response.data;

                            if (specific === '') {
                                setIsOpen(true);
                                setDateStr(info.dateStr);
                            } else {
                                if (specific.dayOff) {
                                    if(window.confirm('휴무를 해제하시겠습니까?')) {
                                        deleteSpecificScheduleById(specific.id).then((response) => {
                                            alert('휴무가 해제되었습니다.');
                                            window.location.reload();
                                        }).catch((error) => {
                                            errorHandle(error, navigate);
                                        })
                                    }
                                } else {
                                    setSpecificId(specific.id);
                                    setIsSpecificOpen(true);
                                    setEachTimes(specific.times);
                                    setName(specific.name);
                                }
                            }

                        }).catch((error) => {
                            errorHandle(error, navigate);
                        });
                    }}
                    events={events}
                    datesSet={(info) => {
                        if (start !== info.startStr && end !== info.endStr) {
                            getScheduleSetByBox().then((res1) => {

                                const startParam = info.startStr.substring(0, 10);
                                let endParam = new Date(info.end);
                                endParam.setDate(endParam.getDate() - 1);
                                endParam = endParam.toLocaleDateString().replace(/ /g, "").replace('.', '-').replace('.', '-').replace('.', '');

                                getSpecificSchedule(startParam, endParam).then((res2) => {

                                    let current = new Date(info.start);
                                    const endDay = new Date(info.end);
                                    const tempEvents = [];
                                    const scheduleSet = res1.data;

                                    while (true) {
                                        if (current.getDate() === endDay.getDate() && current.getMonth() + 1 === endDay.getMonth() + 1) {
                                            break;
                                        } else {
                                            const day = current.getDay();
                                            let event;

                                            const date = current.getDate().toString().length === 1 ? '0' + current.getDate() : current.getDate();
                                            const month = Number(current.getMonth() + 1).toString().length === 1 ? '0' + Number(current.getMonth() + 1) : Number(current.getMonth() + 1);
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

                                            for (const specific of res2.data) {
                                                if (monthDate === specific.date) {
                                                    if (specific.dayOff) {
                                                        event = {title: '휴무', date: monthDate, color: 'red' }
                                                    } else {
                                                        event = { title: specific.name, date: monthDate, color: 'purple' }
                                                    }
                                                }
                                            }

                                            tempEvents.push(event);
                                            current = new Date(current.setDate(current.getDate() + 1));
                                        }
                                    }
                                    setEvents(tempEvents);
                                    setStart(info.startStr);
                                    setEnd(info.endStr);
                                }).catch((error) => {
                                    errorHandle(error, navigate);
                                })
                            }).catch((error) => {
                                errorHandle(error, navigate);
                            });
                        }
                    }
                    }
                />
            </div>
            <WriteSpecificScheduleModal isOpen={isOpen} setIsOpen={setIsOpen} dateStr={dateStr} />
            <ReadSpecificScheduleModal isSpecificOpen={isSpecificOpen} setIsSpecificOpen={setIsSpecificOpen} eachTimes={eachTimes} name={name} id={specificId} />
        </div>
    );
}