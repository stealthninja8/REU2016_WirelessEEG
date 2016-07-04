% analysis1.m
% Steve Heinisch, Zoe Kendall, and Sam Morris
% This script [will] read in an Excel file and filter the contained data,
% then epoch it into 1-s segments. Epochs will then be analyzed by an FFT
% and averaged.

close all;
home;
% ========================================================================
%                               CONSTANTS
% ========================================================================
filename = 'C:\Users\zkendall\zkendall\Documents\SPIRE-EIT\srest1.xlsx';
desired_electrode = 'Cz';

sampleRate = 2048;      % Hz
duration = 10;
N = sampleRate*duration;

% ========================================================================
%                                SCRIPT
% ========================================================================
[num, txt, raw] = xlsread(filename);

% read data and get the electrode labels as a cell array
labels = raw(9, 1:end);
frames = num(7:end, 1);
time = frames ./ sampleRate;

% find the column number of the desired electrode
elec = 1;
for i = 1:length(labels)
    if strcmp(labels(i), desired_electrode) == 1
        elec = i;
        break;
    end
end
% elec
% labels(elec)
data = num(7:end,1:end);
doi = data(1:end, elec);

% Hopefully, this will filter out the electricity.
d = fdesign.notch(2, 60, 35, 2048);
Hd = design(d);
dataOut = filter(Hd, doi);

% Hopefully, the next two lines bandpass filters our data.
bpFilt = designfilt('bandpassFIR', 'FilterOrder', 20, 'CutoffFrequency1', 1, 'CutoffFrequency2', 65, 'SampleRate', sampleRate);
newData = filter(bpFilt,dataOut);

[b,a] = butter(2, 1*2/sampleRate);
butterData1 = filter(b,a,dataOut);
[x,y] = butter(2, 62.5*2/sampleRate);
butterData2 = filter(x,y,butterData1);

% lapl = fspecial(laplacian) //makes the kernel/filter
% finalData = filter2(lapl, butterData2) //apply the filter you just made

% [num,txt,raw] = xlsread(filename, 'F9:F9')
% if strcmp(txt, 'F1') == 1
%     raw = xlsread(filename, 'F:F');
%     x = 1
% end

% laplac = eeg_laplac(butterData2, 1);

% X = fft(butterData2);

% plot the data
figure(1)
plot(time, doi)
title('Original Signal')
xlabel('Time (s)')
ylabel('EEG (V)')

figure(2)
%subplot(2,1,1), plot(time, dataOut)
%subplot(2,1,2), plot(time, newData)
plot(time, newData)
xlabel('Time (s)')
ylabel('EEG (V)')
ylim([1e-3, 1.6e-3])

figure(3)
plot(time, butterData2)
ylim([1e-3,1.6e-3])