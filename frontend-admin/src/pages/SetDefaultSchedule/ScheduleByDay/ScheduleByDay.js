import styles from './ScheduleByDay.module.css';
import React, { useEffect, useState } from 'react';
import { updateScheduleSet } from '../../../api/pages/SetDefaultSchedule/ScheduleByDay/ScheduleByDay';
import { errorHandle } from '../../../util/util';
import { useNavigate } from 'react-router-dom';
import { getScheduleSetByBox } from '../../../api/pages/SetDefaultSchedule/SetDefaultSchedule';

export default function ScheduleByDay({ scheduleSet, schedules, setScheduleSet }) {

    let navigate = useNavigate();

    const [scheduleSetId, setScheduleSetId] = useState(null);

    useEffect(() => {
        if (scheduleSet !== null) {
            setScheduleSetId(scheduleSet.id);
        }
    }, [scheduleSet])

    function SelectBox({ schedules, day, selected }) {
        if (schedules !== undefined && schedules !== null) {
            return (
                <>
                    <select defaultValue={selected}
                        className={styles.select}
                        onChange={(e) => {
                            let scheduleId = e.target.value;

                            if (scheduleId === 'holiday') {
                                scheduleId = -1;
                            }

                            updateScheduleSet(scheduleSetId, day, scheduleId).then((response) => {
                                getScheduleSetByBox().then((response) => {
                                    setScheduleSet(response.data);
                                }).catch((error) => {
                                    errorHandle(error, navigate);
                                });
                            }).catch((error) => {
                                errorHandle(error, navigate);
                            });
                        }}>
                        {
                            schedules.map((schedule) => (
                                <option className={styles.option}
                                    key={schedule.id}
                                    value={schedule.id}
                                >
                                    {schedule.name}
                                </option>
                            ))
                        }
                        <option className={styles.holiday} key={'holiday'} value={'holiday'}>휴일</option>
                    </select>
                </>
            );
        } else {
            return null;
        }
    }

    if (scheduleSet !== undefined && scheduleSet !== null) {
        return (
            <>
                <tbody>
                    <tr>
                        <td className={styles.td2}><span>월요일</span></td>
                        <td className={styles.td3}>
                            {
                                scheduleSet !== null && scheduleSet.monday !== null ?
                                    (<span>{scheduleSet.monday.name}</span>) :
                                    (<span className={styles.holiday}>{'휴일'}</span>)
                            }
                        </td>
                        <td className={styles.td4}>
                            {
                                scheduleSet !== null && scheduleSet.monday !== null ?
                                    (<SelectBox schedules={schedules} selected={scheduleSet.monday.id} day={'monday'} />) :
                                    (<SelectBox schedules={schedules} selected={'holiday'} day={'monday'} />)
                            }
                        </td>
                    </tr>

                    <tr>
                        <td className={styles.td2}><span>화요일</span></td>
                        <td className={styles.td3}>
                            {
                                scheduleSet !== null && scheduleSet.tuesday !== null ?
                                    (<span>{scheduleSet.tuesday.name}</span>) :
                                    (<span className={styles.holiday}>{'휴일'}</span>)
                            }
                        </td>
                        <td className={styles.td4}>
                            {
                                scheduleSet !== null && scheduleSet.tuesday !== null ?
                                    (<SelectBox schedules={schedules} selected={scheduleSet.tuesday.id} day={'tuesday'} />) :
                                    (<SelectBox schedules={schedules} selected={'holiday'} day={'tuesday'} />)
                            }
                        </td>
                    </tr>

                    <tr>
                        <td className={styles.td2}><span>수요일</span></td>
                        <td className={styles.td3}>
                            {
                                scheduleSet !== null && scheduleSet.wednesday !== null ?
                                    (<span>{scheduleSet.wednesday.name}</span>) :
                                    (<span className={styles.holiday}>{'휴일'}</span>)
                            }
                        </td>
                        <td className={styles.td4}>
                            {
                                scheduleSet !== null && scheduleSet.wednesday !== null ?
                                    (<SelectBox schedules={schedules} selected={scheduleSet.wednesday.id} day={'wednesday'} />) :
                                    (<SelectBox schedules={schedules} selected={'holiday'} day={'wednesday'} />)
                            }
                        </td>
                    </tr>

                    <tr>
                        <td className={styles.td2}><span>목요일</span></td>
                        <td className={styles.td3}>
                            {
                                scheduleSet !== null && scheduleSet.thursday !== null ?
                                    (<span>{scheduleSet.thursday.name}</span>) :
                                    (<span className={styles.holiday}>{'휴일'}</span>)
                            }
                        </td>
                        <td className={styles.td4}>
                            {
                                scheduleSet !== null && scheduleSet.thursday !== null ?
                                    (<SelectBox schedules={schedules} selected={scheduleSet.thursday.id} day={'thursday'} />) :
                                    (<SelectBox schedules={schedules} selected={'holiday'} day={'thursday'} />)
                            }
                        </td>
                    </tr>

                    <tr>
                        <td className={styles.td2}><span>금요일</span></td>
                        <td className={styles.td3}>
                            {
                                scheduleSet !== null && scheduleSet.friday !== null ?
                                    (<span>{scheduleSet.friday.name}</span>) :
                                    (<span className={styles.holiday}>{'휴일'}</span>)
                            }
                        </td>
                        <td className={styles.td4}>
                            {
                                scheduleSet !== null && scheduleSet.friday !== null ?
                                    (<SelectBox schedules={schedules} selected={scheduleSet.friday.id} day={'friday'} />) :
                                    (<SelectBox schedules={schedules} selected={'holiday'} day={'friday'} />)
                            }
                        </td>
                    </tr>

                    <tr>
                        <td className={styles.td2}><span>토요일</span></td>
                        <td className={styles.td3}>
                            {
                                scheduleSet !== null && scheduleSet.saturady !== null ?
                                    (<span>{scheduleSet.saturady.name}</span>) :
                                    (<span className={styles.holiday}>{'휴일'}</span>)
                            }
                        </td>
                        <td className={styles.td4}>
                            {
                                scheduleSet !== null && scheduleSet.saturady !== null ?
                                    (<SelectBox schedules={schedules} selected={scheduleSet.saturady.id} day={'saturady'} />) :
                                    (<SelectBox schedules={schedules} selected={'holiday'} day={'saturady'} />)
                            }
                        </td>
                    </tr>

                    <tr>
                        <td className={styles.td2}><span>일요일</span></td>
                        <td className={styles.td3}>
                            {
                                scheduleSet !== null && scheduleSet.sunday !== null ?
                                    (<span>{scheduleSet.sunday.name}</span>) :
                                    (<span className={styles.holiday}>{'휴일'}</span>)
                            }
                        </td>
                        <td className={styles.td4}>
                            {
                                scheduleSet !== null && scheduleSet.sunday !== null ?
                                    (<SelectBox schedules={schedules} selected={scheduleSet.sunday.id} day={'sunday'} />) :
                                    (<SelectBox schedules={schedules} selected={'holiday'} day={'sunday'} />)
                            }
                        </td>
                    </tr>
                </tbody>
            </>
        );
    } else {
        return null;
    }
}