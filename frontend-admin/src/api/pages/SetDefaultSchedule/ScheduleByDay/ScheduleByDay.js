import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const updateScheduleSet = async (scheduleSetId, day, scheduleId) => {

    const data = {
        scheduleSetId: scheduleSetId,
        day: day,
        scheduleId: scheduleId,
    }

    const config = {
        method: 'PUT',
        url: `${backEndBaseUri}/admin/api/schedule-set`,
        data: data,
        withCredentials: true,
    }

    return await axios(config);
};

export { updateScheduleSet };