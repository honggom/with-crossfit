import styles from './SetDefaultSchedule.module.css';

export default function SetDefaultSchedule() {

    return (
        <div className={styles.wrapper}>

            <div className={styles.titleWrapper1}>
                <span>기본 일정 설정</span>
            </div>

            <div className={styles.titleWrapper2}>
                <span>저장된 기본 일정</span>
            </div>

            <div className={styles.defaultScheduleWrapper}>
                <table className={styles.table}>
                    <thead>
                        <tr>
                            <th className={styles.th1}>이름</th>
                            <th className={styles.th2}>
                                <button className={styles.addButton}>+</button>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td className={styles.td} colSpan={2}><span>평일</span></td>
                        </tr>

                        <tr>
                            <td className={styles.td} colSpan={2}><span>토요일</span></td>
                        </tr>

                        <tr>
                            <td className={styles.td} colSpan={2}><span>휴일</span></td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div className={styles.titleWrapper3}>
                <span>요일별 일정</span>
            </div>

            <div className={styles.weekWrapper}>
                <table className={styles.table}>
                    <thead>
                        <tr>
                            <th className={styles.th3}>요일</th>
                            <th className={styles.th4}>설정된 일정</th>
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
        </div>
    );
}